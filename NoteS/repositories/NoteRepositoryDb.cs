using Microsoft.EntityFrameworkCore;
using NoteS.Models;

namespace NoteS.repositories;

public partial class NoteRepositoryDbAndElastic
{
    public partial Note Save(Note note)
    {
        throw new NotImplementedException();
    }

    private partial bool DeleteInDb(Note note)
    {
        throw new NotImplementedException();
    }

    public partial Note? FindByPath(string path)
    {
        throw new NotImplementedException();
    }

    public partial Note LoadTags(Note note)
    {
        throw new NotImplementedException();
    }

    public partial bool DeleteTag(Note note, Tag tag)
    {
        throw new NotImplementedException();
    }

    public partial Tag AddTag(Note note, Tag tag)
    {
        throw new NotImplementedException();
    }

    public partial bool IsTagExists(string name, Note note)
    {
        throw new NotImplementedException();
    }

    public partial List<Note> LoadComments(string path, Account owner)
    {
        throw new NotImplementedException();
    }

    public partial List<Note> FindByTag(string title, Account owner)
    {
        throw new NotImplementedException();
    }

    public partial Task<List<Note>> FindByOwner(Account owner)
    {
        throw new NotImplementedException();
    }
}