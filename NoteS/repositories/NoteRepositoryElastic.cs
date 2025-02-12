using Elastic.Clients.Elasticsearch;
using Elastic.Clients.Elasticsearch.Core.Search;
using Elastic.Clients.Elasticsearch.QueryDsl;
using Microsoft.OpenApi.Extensions;
using NoteS.exceptions;
using NoteS.models.dto;
using NoteS.models.entity;
using NoteS.tools;

namespace NoteS.repositories;

public partial class NoteRepositoryDbAndElastic
{
    private string IndexName { get; } = elasticData.Name;
    private string Model { get; } = elasticData.Model;

    private async partial Task<bool> DeleteInElastic(Note note)
    {
        if (note.Id == null) return false;
        var response = await client.DeleteAsync<Note>(note.Id);
        return response.IsValidResponse;
    }

    private async partial Task<Note> SaveInElastic(Note note)
    {
        var content = note.ElasticUuid == null ? null : await GetContent(note);
        if (content == null)
        {
            var result = await CreateInElastic(note, note);
            note.ElasticUuid = result.ElasticUuid;
        }
        else
        {
            var resp = await client.UpdateAsync<ElasticResponseDto, ElasticRequestDto>(IndexName, note.ElasticUuid,
                r => r.Doc(new ElasticRequestDto
                {
                    title = note.Title,
                    content = note.Content ?? throw new ArgumentNullException(nameof(note.Content)),
                    syntax_type = note.SyntaxType?.Name.GetDisplayName() ??
                                  throw new ArgumentNullException(nameof(note.SyntaxType))
                }));
            if (!resp.IsValidResponse)
                throw new NotFound("контент"); //TODO: лучше перенести это "наверх". Плюс ошибка неправильная
            note.ElasticUuid = resp.Id;
        }

        return note;
    }

    public async partial Task<NoteContentDto?> GetContent(NoteElasticDto note)
    {
        var response = await client.SearchAsync<ElasticResponseDto>(r => r
            .Index(IndexName)
            .Query(q => q
                .Term(t => t
                    .Field(e => e._id)
                    .Value(note.ElasticUuid)
                )
            ));
        if (!response.IsValidResponse)
        {
            return null; //TODO: Еластик ошибку надо
        }

        var doc = response.Documents.FirstOrDefault();
        return doc;
    }

    private async partial Task<PageDto<Note>> LoadContentElastic(PageDto<Note> notes)
    {
        var values = notes.items.Select(note => FieldValue.String(note.ElasticUuid)).ToArray();
        var response = await client.SearchAsync<ElasticResponseDto>(r => r
            .Index(IndexName)
            .Query(q => q
                .Terms(t => t
                    .Field(e => e._id)
                    .Terms(new TermsQueryField(values))
                )
            ));
        if (!response.IsValidResponse)
        {
            return notes; //TODO: Еластик ошибку надо
        }


        foreach (var note in notes.items)
        {
            note.Content = response.Hits
                .First(hit => hit.Id == note.ElasticUuid).Source?.Content;
        }

        return notes;
    }

    public async partial Task<PageDto<SearchResultDto>> FindByTitleElastic(NoteTitleDto title, AccIdDto ownerId,
        PageSizeDto page,
        LimitDto limit)
    {
        var response = await client.SearchAsync<ElasticResponseDto>(s => s
            .Index(IndexName)
            .Query(q => q
                .Bool(b => b
                    .Must(mu => mu
                            .QueryString(t => t
                                .Query($"*{title.Title}*")
                                .Fields("title")
                            ),
                        mu => mu.Term(t => t
                            .Field("owner")
                            .Value(ownerId.Id)
                        )
                    )
                )
            )
            .TrackTotalHits(new TrackHits(true))
            .From(CalculateUtil.ToOffset(page.Page, limit.Limit))
            .Size(limit.Limit)
        );
        if (!response.IsValidResponse)
        {
            throw new NotFound("Записка"); //TODO: кривое исключение
        }

        var l = response.Hits.ToList().Select(d => new SearchResultDto(d.Id)
        {
            Content = d.Source?.Content ?? "",
            Score = (decimal)(d.Score ?? 1)
        }).ToList();
        
        return new PageDto<SearchResultDto>
        {
            items = l,
            Total = response.Total,
            Page = CalculateUtil.CurrentPage(page.Page, limit.Limit, response.Total)
        };
    }

    private async partial Task<PageDto<SearchResultDto>> SemanticFindInElastic(SemanticSearchQuery find,
        AccIdDto ownerId,
        PageSizeDto page,
        LimitDto limit)
    {
        var response = await client.SearchAsync<ElasticResponseDto>(s => s
            .Index("notes")
            .Knn(k => k
                .Field("vector.vector")
                .k(10)
                .NumCandidates(100)
                .QueryVectorBuilder(qvb => qvb
                    .TextEmbedding(te => te
                        .ModelId(Model)
                        .ModelText($"*{find.Query}*")
                    )
                )
                .Filter(f => f.Bool(
                    b => b.Must(
                        mn => mn.Term(t => t
                            .Field("owner")
                            .Value(ownerId.Id)
                        ), mn => mn.Term(t => t
                            .Field("entry_type")
                            .Value(NoteTypes.Note.Name.GetDisplayName()))))
                )
            )
            .TrackTotalHits(new TrackHits(true))
            .From(CalculateUtil.ToOffset(page.Page, limit.Limit))
            .Size(limit.Limit)
        );
        if (!response.IsValidResponse)
        {
            throw new NotFound("Записка");
        }

        var l = response.Hits.ToList().Select(d => new SearchResultDto(d.Id)
        {
            Content = d.Source?.Content ?? "",
            Score = (decimal)(d.Score ?? 1)
        }).ToList();

        return new PageDto<SearchResultDto>
        {
            items = l,
            Total = response.Total,
            Page = CalculateUtil.CurrentPage(page.Page, limit.Limit, response.Total)
        };
    }

    private async partial Task<NoteElasticDto> CreateInElastic(Note note, AccIdDto owner)
    {
        var uuid = Guid.NewGuid().ToString();
        var response = await client.CreateAsync(new
        {
            title = note.Title,
            content = note.Content,
            syntax_type = note.SyntaxType?.Name.GetDisplayName(),
            entry_type = note.Type?.Name.GetDisplayName(),
            owner = owner.Id
        }, c => c.Index(IndexName).Id(uuid));

        return new NoteElasticDto(response.Id);
    }
}