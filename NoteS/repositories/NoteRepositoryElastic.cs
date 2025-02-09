using NoteS.exceptions;
using NoteS.Models;
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
   
    public async partial Task<Note> LoadContent(Note note)
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
            throw new NotFound("Записка");
        }
        return response.Documents.FirstOrDefault() ?? throw new NotFound("Записка");
    }

    private partial List<Note> LoadContent(List<Note> notes)
    {
        return [];
    }

    public async partial Task<List<Note>> FindByTitle(Field<INoteContent, string> content, int ownerId)
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

        return response.Documents.ToList();
    }

    public async partial Task<List<Note>> SemanticFind(SemanticSearchQuery find, int ownerId)
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

        return response.Documents.ToList();
    }

    public async partial Task<Note> CreateInElastic(NoteCreateRequestDto requestDto, Account owner)
    {
        var response = await client.CreateAsync(new
        {
            title = requestDto.Title,
            content = requestDto.Content,
            syntax_type = requestDto.Type.Name,
            owner = owner.Id
        });
        return new Note(requestDto.Title, response.Id)
        {
            Owner = owner,
            Content = requestDto.Content,
            SyntaxType = requestDto.Type,
        };
    }
}