using NoteS.Models;
using NoteS.models.dto;

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

    private partial List<Note> LoadContent(List<Note> notes)
    {
        return notes.Select(LoadContent).ToList();
    }

    public partial List<Note> FindByTitle(string title, Account owner)
    {
        return [];
    }

    public partial void SaveContent(string content)
    {
    }

    public partial List<Note> SemanticFind(string find, Account owner)
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