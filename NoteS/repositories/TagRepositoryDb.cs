using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.ChangeTracking;
using NoteS.Models;

namespace NoteS.repositories;

public sealed class TagRepositoryDb(DbContextOptions<TagRepositoryDb> options)
    : DbContext(options), ITagRepository
{
    public Tag Save(Tag tag)
    {
        throw new NotImplementedException();
    }

    public List<Note> FindNotes(Tag tag)
    {
        throw new NotImplementedException();
    }

    public Tag FindByName(string name)
    {
        throw new NotImplementedException();
    }
}