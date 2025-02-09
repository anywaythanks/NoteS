using Microsoft.EntityFrameworkCore;
using NoteS.models.entity;

namespace NoteS.repositories;

public sealed class TagRepositoryDb(DbContextOptions<TagRepositoryDb> options)
    : DbContext(options), ITagRepository
{
    public Tag Save(Tag tag)
    {
        throw new NotImplementedException();
    }

    public bool Delete(Tag note)
    {
        throw new NotImplementedException();
    }

    public List<Note> FindNotes(Tag tag)
    {
        throw new NotImplementedException();
    }

    public List<Tag> FindByOwner(Account owner)
    {
        throw new NotImplementedException();
    }

    public Tag? FindByName(Field<ITagName, string> name, Account owner)
    {
        throw new NotImplementedException();
    }

    public Tag FindByName(Field<ITagName, string> name)
    {
        throw new NotImplementedException();
    }
}