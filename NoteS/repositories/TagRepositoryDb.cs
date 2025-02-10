using System.Diagnostics.CodeAnalysis;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using NoteS.models.entity;

namespace NoteS.repositories;

public sealed class TagRepositoryDb(DbContextOptions<TagRepositoryDb> options)
    : DbContext(options), ITagRepository
{
    public DbSet<Tag> Tags { get; init; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Tag>()
            .Property(e => e.Name)
            .HasConversion(
                new ValueConverter<string, string>(v => v.TrimEnd(), v => v.TrimEnd()));
    }

    public Tag Save(Tag tag)
    {
        var t = tag;
        if (tag.Id != null)
        {
            Tags.Add(t);
            Tags.Update(t);
        }
        else
        {
            t = Tags.Add(tag).Entity;
        }

        SaveChanges();
        return Detach(t);
    }

    public List<Tag> FindByOwner(Account owner)
    {
        return (from t in Tags
                where t.Owner == owner
                select t)
            .Select(t => Detach(t))
            .ToList();
    }

    public Tag? FindByName(Field<ITagName, string> name, Account owner)
    {
        var tag = Detach((from t in Tags
            where t.Name == name.Val && t.Owner.Id == owner.Id
            select t).FirstOrDefault());
        if (tag == null) return null;
        tag.Owner = owner;
        return tag;
    }

    [return: NotNullIfNotNull(nameof(tag))]
    private Tag? Detach(Tag? tag)
    {
        if (tag == null) return null;
        var entry = Entry(tag);
        entry.State = EntityState.Detached;
        return entry.Entity;
    }
}