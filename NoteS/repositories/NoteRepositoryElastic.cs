using Elastic.Clients.Elasticsearch;
using NoteS.exceptions;
using NoteS.models.dto;
using NoteS.models.entity;

namespace NoteS.repositories;

public partial class NoteRepositoryDbAndElastic
{
    private async partial Task<bool> DeleteInElastic(Note note)
    {
        if (note.Id == null) return false;
        var response = await client.DeleteAsync<Note>(note.Id);
        return response.IsValidResponse;
    }

    public async partial Task<Note> SaveContent(Note note)
    {
        var result = await client.CreateAsync(note);
        note.ElasticUuid = result.Id;
        return note;
    }

    public async partial Task<NoteContentDto?> GetContent(NoteElasticDto note)
    {
        var response = await client.SearchAsync<Note>(r => r
            .Query(q =>
                q.Script(s =>
                    s.Script(script =>
                        script.Params(p =>
                            p.Add("id", note.ElasticUuid)
                        )))));
        if (!response.IsValidResponse)
        {
            return null; //TODO: Еластик ошибку надо
        }

        return response.Documents.FirstOrDefault();
    }

    private partial PageDto<Note> LoadContent(PageDto<Note> notes)
    {
        return notes;
    }

    public async partial Task<PageDto<Note>> FindByTitle(NoteTitleDto title, AccIdDto ownerId, PageSizeDto page,
        LimitDto limit)
    {
        var response = await client.SearchAsync<Note>(r => r
            .Query(q =>
                q.Script(s =>
                    s.Script(script =>
                        script.Params(p =>
                            p.Add("title", title)
                                .Add("owner", ownerId)
                        )))));
        if (!response.IsValidResponse)
        {
            throw new NotFound("Записка");
        }

        var l = response.Documents.ToList();

        return new PageDto<Note>
        {
            items = l,
            Total = 1,
            Page = 1
        };
    }

    public async partial Task<PageDto<Note>> SemanticFind(SemanticSearchQuery find, AccIdDto ownerId, PageSizeDto page,
        LimitDto limit)
    {
        var response = await client.SearchAsync<Note>(r => r
            .Query(q =>
                q.Script(s =>
                    s.Script(script =>
                        script.Params(p =>
                            p.Add("query", find)
                                .Add("owner", ownerId)
                        )))));
        if (!response.IsValidResponse)
        {
            throw new NotFound("Записка");
        }

        var l = response.Documents.ToList();
        return new PageDto<Note>
        {
            items = l,
            Total = 1,
            Page = 1
        };
    }

    public async partial Task<Note> CreateInElastic(NoteCreateRequestDto requestDto, AccIdDto owner)
    {
        var uuid = Guid.NewGuid().ToString();
        var response = await client.CreateAsync(new
        {
            title = requestDto.Title,
            content = requestDto.Content,
            syntax_type = requestDto.Type.Name,
            owner = owner.Id
        }, c => c.Id(uuid));
        return new Note(requestDto.Title, uuid)
        {
            Owner = owner.Id,
            Content = requestDto.Content,
            SyntaxType = requestDto.Type,
        };
    }
}