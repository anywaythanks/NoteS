using System.Diagnostics.CodeAnalysis;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using NoteS.models.entity;
using NoteS.models.mappers;

namespace NoteS.repositories;

public sealed class TagRepositoryDb(DbContextOptions<TagRepositoryDb> options, TagMapper tm)
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

    public List<Tag> FindByOwner(AccIdDto owner)
    {
        return (from t in Tags
                where t.Owner == owner.Id
                select t)
            .ToList()
            .Select(t => Detach(t))
            .ToList();
    }

    public Tag? FindByName(TagNameDto name, AccIdDto owner)
    {
        var tag = Detach((from t in Tags
            where t.Name == name.Name && t.Owner == owner.Id
            select t).FirstOrDefault());
        if (tag == null) return null;
        tag.Owner = owner.Id;
        return tag;
    }

    List<TagIdDto> ITagRepository.Tags(List<TagNameDto> tags, AccIdDto owner)
    {
        var names = tags.Select(t => t.Name).ToList();
        return (from t in Tags
                where t.Owner == owner.Id && names.Contains(t.Name)
                select t.Id)
            .ToList()
            .Select(t => tm.OfIdDto(t))
            .ToList();
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