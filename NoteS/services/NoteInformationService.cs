using NoteS.exceptions;
using NoteS.models.dto;
using NoteS.models.entity;
using NoteS.models.mappers;
using NoteS.repositories;

namespace NoteS.services;

public class NoteInformationService(
    INoteRepository repository,
    AccountInformationService informationService)
{
    public async Task<PageDto<Note>> Find(NoteTitleDto title, AccNameDto owner, PageSizeDto pageSize, LimitDto limit)
    {
        var acc = informationService.Get(owner);
        var notes = await repository.FindByTitle(title, acc, pageSize, limit);
        notes.items.ForEach(note => note.OwnerAccount = acc);
        return notes;
    }

    public PageDto<Note> Find(AccNameDto owner, PageSizeDto pageSize, LimitDto limit)
    {
        var acc = informationService.Get(owner);
        var notes = repository.FindNotesByOwner(acc, pageSize, limit);
        notes.items.ForEach(note => note.OwnerAccount = acc);
        return notes;
    }

    public async Task<PageDto<Note>> FindSemantic(AccNameDto owner, SemanticSearchQuery query, PageSizeDto page,
        LimitDto limit)
    {
        var acc = informationService.Get(owner);
        var notes = await repository.SemanticFind(query, acc, page, limit);
        notes.items.ForEach(note => note.OwnerAccount = acc);
        return notes;
    }

    public Note Get(NotePathDto path, AccNameDto owner)
    {
        var note = repository.FindByPath(path) ?? throw new NotFound("заметка");
        var acc = informationService.Get(note);
        if (acc.Name != owner.Name) throw new Forbidden("заметке");
        note.OwnerAccount = acc;
        return note;
    }

    public Note GetPublic(NotePathDto path, AccNameDto owner)
    {
        var note = repository.FindByPath(path) ?? throw new NotFound("заметка");
        var acc = informationService.Get(note);
        if (!note.IsPublic && acc.Name != owner.Name) throw new Forbidden("заметке");
        note.OwnerAccount = acc;
        return note;
    }

    public Note Get(NotePathDto path)
    {
        var note = repository.FindByPath(path) ?? throw new NotFound("заметка");
        return note;
    }

    public async Task<Note> GetFullPublic(NotePathDto path, AccNameDto owner)
    {
        var note = GetPublic(path, owner);
        var content = await repository.GetContent(note)
                      ?? throw new NotFound("Контент");
        note.Content = content.Content;
        note.Tags = repository.GetTags(note);
        return note;
    }

    public async Task<Note> GetFull(NotePathDto path)
    {
        var note = Get(path);
        var content = await repository.GetContent(note)
                      ?? throw new NotFound("Контент");
        note.Content = content.Content;
        repository.GetTags(note);
        return note;
    }
}