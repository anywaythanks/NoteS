using NoteS.models.dto;
using NoteS.models.entity;

namespace NoteS.repositories;

public partial class NoteRepositoryDbAndElastic
{
    private partial bool DeleteInElastic(Note note)
    {
        return true;
    }

    public partial Note SaveContent(Note note)
    {
        return note;
    }

    public partial Note LoadContent(Note note)
    {
        return note;
    }

    public partial List<Note> FindByTitle(Field<INoteTitle, string> title, Account owner)
    {
        return [];
    }

    public partial void SaveContent(Field<INoteContent, string> content)
    {
    }

    public partial List<Note> SemanticFind(SemanticSearchQuery find, Account owner)
    {
        return [];
    }

    public partial Note CreateInElastic(NoteCreateRequestDto requestDto, Account owner)
    {
        return new Note(requestDto.Title, Guid.NewGuid().ToString())
        {
            Owner = owner,
            Tags = []
        };
    }
}