using Microsoft.EntityFrameworkCore;
using NoteS.models.entity;

namespace NoteS.repositories;

public partial class NoteRepositoryDbAndElastic(DbContextOptions<NoteRepositoryDbAndElastic> options)
    : DbContext(options)
{
    public partial Note Save(Note note)
    {
        throw new NotImplementedException();
    }

    private partial bool DeleteInDb(Note note)
    {
        throw new NotImplementedException();
    }

    public partial Note? FindByPath(Field<INotePath, string> path)
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

    public partial bool IsTagExists(Field<ITagName, string> name, Note note)
    {
        throw new NotImplementedException();
    }

    public partial List<Note> LoadComments(Field<INotePath, string> path, Account owner)
    {
        throw new NotImplementedException();
    }

    public partial List<Note> FindByTag(Field<ITagName, string> tag, Account owner)
    {
        throw new NotImplementedException();
    }

    public partial List<Note> FindByOwner(Account owner)
    {
        throw new NotImplementedException();
    }
}