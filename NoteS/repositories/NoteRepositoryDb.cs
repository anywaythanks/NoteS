using System.Diagnostics.CodeAnalysis;
using Microsoft.EntityFrameworkCore;
using NoteS.models.entity;

namespace NoteS.repositories;

public partial class NoteRepositoryDbAndElastic
{
    public DbSet<Note> Notes { get; set; }
    public DbSet<Tag> Tags { get; set; }
    public DbSet<NoteTag> NoteTags { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        // modelBuilder.Entity<NoteTag>()
        //     .HasKey(nt => new { nt.NoteId, nt.TagId });

        modelBuilder.Entity<NoteTag>()
            .HasOne(nt => nt.Tag)
            .WithMany(n => n.Notes)
            .HasForeignKey(nt => nt.TagId);

        modelBuilder.Entity<NoteTag>()
            .HasOne(nt => nt.Note)
            .WithMany(n => n.Tags)
            .HasForeignKey(nt => nt.NoteId)
            .OnDelete(DeleteBehavior.Cascade); // Cascade delete when a Note is deleted

        modelBuilder
            .Entity<Note>()
            .Property(e => e.Type)
            .HasConversion(
                v => NoteTypes.TypeToNum(v),
                v => NoteTypes.NumToType(v));

        modelBuilder
            .Entity<Note>()
            .Property(e => e.SyntaxType)
            .HasConversion(
                v => SyntaxType.TypeToNum(v),
                v => SyntaxType.NumToType(v));
    }

    public partial Note Save(Note note)
    {
        bool isNew = note.Id == null;
        note.CreatedOn = null; //делаем null,
        if (isNew)
        {
            Notes.Add(note);
        }
        else
        {
            Notes.Update(note);
        }

        SaveChanges();
        return Detach(note);
    }

    private partial bool DeleteInDb(Note note)
    {
        Notes.Remove(note);
        return SaveChanges() >= 1;
    }

    public partial Note? FindByPath(Field<INotePath, string> path)
    {
        return Detach((from n in Notes
            where n.Path == path.Val
            select n).FirstOrDefault());
    }

    public partial Note LoadTags(Note note)
    {
        note.Tags = (Detach((from n in Notes.Include(n => n.Tags)
                where n.Id == note.Id
                select n).FirstOrDefault())?.Tags ?? [])
            .Select(n => Detach(n))
            .ToList();
        return note;
    }

    public partial bool DeleteTag(Note note, Tag tag)
    {
        var noteTag = NoteTags
            .FirstOrDefault(nt => nt.NoteId == note.Id && nt.TagId == tag.Id);

        if (noteTag != null) NoteTags.Remove(noteTag);


        return SaveChanges() >= 1;
    }

    public partial NoteTag AddTag(Note note, Tag tag)
    {
        var nt = new NoteTag
        {
            Note = note,
            Tag = tag
        };
        if (note.Id != null)
        {
            NoteTags.Add(nt);
            NoteTags.Update(nt);
        }
        else
        {
            nt = NoteTags.Add(nt).Entity;
        }

        SaveChanges();
        return Detach(nt);
    }

    public partial bool IsTagExists(Field<ITagId, int> tag, Note note)
    {
        var noteTag = NoteTags
            .FirstOrDefault(nt => nt.NoteId == note.Id && nt.TagId == tag.Val);
        return noteTag != null;
    }

    private partial List<Note> LoadCommentsInDb(Note note)
    {
        return (from n in Notes
                where n.Owner.Id == note.Id &&
                      n.Type == NoteTypes.Comment &&
                      n.IsPublic == true
                select n)
            .Select(n => Detach(n))
            .ToList();
    }

    public partial List<Note> FindByTag(Field<ITagId, int> tag, Account owner)
    {
        return (Detach((from t in Tags.Include(n => n.Notes)
                where t.Id == tag.Val && t.Owner.Id == owner.Id
                select t).FirstOrDefault())?.Notes ?? [])
            .Select(nt => Detach(nt))
            .Select(nt => nt.Note)
            .ToList();
    }

    public partial List<Note> FindByOwner(Account owner)
    {
        return (from n in Notes
                where n.Owner.Id == owner.Id
                select n)
            .Select(n => Detach(n))
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

    [return: NotNullIfNotNull(nameof(ntg))]
    private NoteTag? Detach(NoteTag? ntg)
    {
        if (ntg == null) return null;
        var entry = Entry(ntg);
        entry.State = EntityState.Detached;
        return entry.Entity;
    }

    [return: NotNullIfNotNull(nameof(note))]
    private Note? Detach(Note? note)
    {
        if (note == null) return null;
        var entry = Entry(note);
        entry.State = EntityState.Detached;
        return entry.Entity;
    }
}