using System.Diagnostics.CodeAnalysis;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using NoteS.models.dto;
using NoteS.models.entity;
using NoteS.tools;

namespace NoteS.repositories;

public partial class NoteRepositoryDbAndElastic
{
    public DbSet<Note> Notes { get; set; }
    public DbSet<Tag> Tags { get; set; }
    public DbSet<NoteTag> NoteTags { get; set; }
    public DbSet<Account> Accounts { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Tag>()
            .HasMany<NoteTag>(nt => nt.Notes)
            .WithOne()
            .HasForeignKey(nt => nt.TagId)
            .OnDelete(DeleteBehavior.Cascade);
        modelBuilder.Entity<Note>()
            .HasMany<NoteTag>(nt => nt.TagsRel)
            .WithOne()
            .HasForeignKey(nt => nt.NoteId)
            .OnDelete(DeleteBehavior.Cascade); // Cascade delete when a Note is deleted
        modelBuilder.Entity<Note>()
            .Ignore(e => e.Content)
            .Ignore(e => e.MainNoteObject)
            .Ignore(e => e.OwnerAccount)
            .Ignore(n => n.Tags)
            .Ignore(n => n.Score);
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

        modelBuilder.Entity<Note>()
            .Property(e => e.ElasticUuid)
            .HasConversion(
                new ValueConverter<string, string>(v => v.TrimEnd(), v => v.TrimEnd()));
        modelBuilder.Entity<Note>()
            .Property(e => e.Path)
            .HasConversion(
                new ValueConverter<string?, string>(v => v.TrimEnd(), v => v.TrimEnd()));
        modelBuilder.Entity<Tag>()
            .Property(e => e.Name)
            .HasConversion(
                new ValueConverter<string, string>(v => v.TrimEnd(), v => v.TrimEnd()));
        modelBuilder.Entity<Note>()
            .Property(e => e.Title)
            .HasConversion(
                new ValueConverter<string, string>(v => v.TrimEnd(), v => v.TrimEnd()));
        modelBuilder.Entity<Note>()
            .Property(e => e.Description)
            .HasConversion(
                new ValueConverter<string, string>(v => v.TrimEnd(), v => v.TrimEnd()));
    }

    public partial Note SavePartial(Note note)
    {
        bool isNew = note.Id == null;
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

    public partial Note? FindByPath(NotePathDto path)
    {
        return Detach((from n in Notes
            where n.Path == path.Path
            select n).FirstOrDefault());
    }

    public partial List<Tag> GetTags(NoteIdDto note)
    {
        var tags = (Detach((from n in Notes.Include(n => n.TagsRel)
                where n.Id == note.Id
                select n).FirstOrDefault())?.TagsRel ?? [])
            .ToList()
            .Select(n => Detach(n))
            .Select(n => n.TagId)
            .ToList();
        return (from t in Tags
                where tags.Contains((int)t.Id)
                select t)
            .ToList()
            .Select(t => Detach(t))
            .ToList();
    }

    public partial bool DeleteTag(NoteIdDto note, TagIdDto tag)
    {
        var noteTag = NoteTags
            .FirstOrDefault(nt => nt.NoteId == note.Id && nt.TagId == tag.Id);

        if (noteTag != null) NoteTags.Remove(noteTag);


        return SaveChanges() >= 1;
    }

    public partial bool AddTag(NoteIdDto note, TagIdDto tag)
    {
        var nt = NoteTags.Add(new NoteTag
        {
            TagId = tag.Id,
            NoteId = note.Id
        }).Entity;
        bool res = SaveChanges() >= 1;
        Detach(nt);
        return res;
    }

    public partial Note? FindById(NoteIdDto note)
    {
        return Detach((from n in Notes
            where n.Id == note.Id
            select n).FirstOrDefault());
    }

    public partial bool IsTagExists(TagIdDto tag, NoteIdDto note)
    {
        var noteTag = NoteTags
            .FirstOrDefault(nt => nt.NoteId == note.Id && nt.TagId == tag.Id);
        return noteTag != null;
    }

    private partial PageDto<Note> GetCommentsInDb(NoteIdDto note, PageSizeDto pageSize, LimitDto limit1)
    {
        var q = from n in Notes
            where n.MainNote == note.Id &&
                  (n.Type == NoteTypes.Comment || n.Type == NoteTypes.CommentRedacted) &&
                  n.IsPublic == true //TODO: Условие потенциально может вызвать кучу проблем. 
            join a in Accounts on n.Owner equals a.Id
            select new { a, n };
        q = q.OrderBy(e => e.n.Id);
        int total = q.Count();
        var limit = limit1.Limit;
        var page = pageSize.Page;
        return new PageDto<Note>
        {
            items = q.Skip(CalculateUtil.ToOffset(page, limit))
                .Take(limit)
                .ToList()
                .Select(o =>
                {
                    o.n.OwnerAccount = o.a;
                    return o.n;
                })
                .ToList(),
            TotalPages = CalculateUtil.TotalPages(total, limit),
            Total = total,
            Page = CalculateUtil.CurrentPage(page, limit, total)
        };
    }

    public partial PageDto<Note> FindNotesByOwner(AccIdDto owner, PageSizeDto pageSize, LimitDto limit1)
    {
        var q = from n in Notes
            where n.Owner == owner.Id &&
                  n.Type == NoteTypes.Note
            select n;
        q = q.OrderBy(e => e.Id);
        int total = q.Count();
        var limit = limit1.Limit;
        var page = pageSize.Page;
        return new PageDto<Note>
        {
            items = q.Skip(CalculateUtil.ToOffset(page, limit))
                .Take(limit)
                .ToList(),
            TotalPages = CalculateUtil.TotalPages(total, limit),
            Total = total,
            Page = CalculateUtil.CurrentPage(page, limit, total)
        };
    }

    public partial PageDto<Note> FindNotesByTags(List<TagIdDto> tags, List<TagIdDto> filterTags, bool op,
        AccIdDto owner, LimitDto limit1, PageSizeDto page1)
    {
        var tagsI = tags.Select(t => t.Id).ToList();
        var filterI = filterTags.Select(t => t.Id).ToList();
        IQueryable<Note> q;
        if (op)
        {
            q = from n in Notes
                where n.Owner == owner.Id && n.Type == NoteTypes.Note &&
                      tagsI.All(requiredTag => n.TagsRel.Any(nTag => nTag.TagId == requiredTag)) &&
                      !filterI.Any(filterTag => n.TagsRel.Any(nTag => nTag.TagId == filterTag))
                select n;
        }
        else
        {
            q = from n in Notes
                where n.Owner == owner.Id && n.Type == NoteTypes.Note &&
                      tagsI.Any(requiredTag => n.TagsRel.Any(nTag => nTag.TagId == requiredTag)) &&
                      !filterI.Any(filterTag => n.TagsRel.Any(nTag => nTag.TagId == filterTag))
                select n;
        }

        q = q.OrderBy(e => e.Id);
        int total = q.Count();
        var limit = limit1.Limit;
        var page = page1.Page;
        q = q.OrderBy(n => n.Id);

        return new PageDto<Note>
        {
            items = q.Skip(CalculateUtil.ToOffset(page, limit))
                .Take(limit)
                .ToList(),
            TotalPages = CalculateUtil.TotalPages(total, limit),
            Total = total,
            Page = CalculateUtil.CurrentPage(page, limit, total)
        };
    }

    private partial PageDto<Note> LoadNoteDb(PageDto<SearchResultDto> notes, PageSizeDto page1, LimitDto limit1)
    {
        var uuids = notes.items.Select(t => t.ElasticUuid).ToList();
        var q = from n in Notes
            where uuids.Contains(n.ElasticUuid)
            select n;

        long total = notes.TotalPages;
        var limit = limit1.Limit;
        var page = page1.Page;
        q = q.OrderBy(n => n.Id);
        var list = q.Skip(CalculateUtil.ToOffset(page, limit))
            .Take(limit)
            .ToList();
        foreach (var note in notes.items)
        {
            var noteDb = list.FirstOrDefault(n => n.ElasticUuid == note.ElasticUuid);
            if (noteDb == null) continue; //TODO: вообще странный случай
            noteDb.Content = note.Content;
            noteDb.Score = note.Score;
        }

        return new PageDto<Note>
        {
            items = list,
            TotalPages = CalculateUtil.TotalPages(total, limit),
            Total = total,
            Page = CalculateUtil.CurrentPage(page, limit, total)
        };
    }

    public partial PageDto<Note> LoadTags(PageDto<Note> notes)
    {
        var notesId = notes.items
            .Where(i => i.Id != null)
            .Select(t => (int)t.Id!)
            .ToList();

        var q = from nt in NoteTags
            where notesId.Contains(nt.NoteId)
            join t in Tags on nt.TagId equals t.Id
            select new { nt, t };
        var list = q.ToList();
        foreach (var l in list)
        {
            var note = notes.items.Find(n => n.Id == l.nt.NoteId);
            if (note != null)
            {
                note.Tags.Add(l.t);
            }
        }

        return notes;
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