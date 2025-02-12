using NoteS.exceptions;
using NoteS.models.dto;
using NoteS.models.entity;
using NoteS.models.mappers;
using NoteS.repositories;

namespace NoteS.services;

public class TagInformationService(
    AccountInformationService accountInformationService,
    NoteInformationService noteInformationService,
    INoteRepository noteRepository,
    ITagRepository tagRepository)
{
    public List<Tag> Tags(AccNameDto accountName)
    {
        var account = accountInformationService.Get(accountName);
        return tagRepository.FindByOwner(account);
    }

    public List<Tag> Tags(NotePathDto pathNote, AccNameDto accountName)
    {
        var note = noteInformationService.Get(pathNote, accountName);
        return noteRepository.GetTags(note);
    }

    public Tag Get(AccNameDto accountName, TagNameDto tag)
    {
        var account = accountInformationService.Get(accountName);
        return tagRepository.FindByName(tag, account) ?? throw new NotFound("тег");
    }

    public PageDto<Note> FindTags(List<TagNameDto> names, List<TagNameDto> filters,
        AccNameDto owner, bool op, LimitDto limit, PageSizeDto page)
    {
        var acc = accountInformationService.Get(owner);
        var ids = tagRepository.Tags(names, acc);
        var fids = tagRepository.Tags(filters, acc);
        if (ids.Count != names.Count || fids.Count != filters.Count) throw new NotFound("теги");
        return noteRepository.FindNotesByTags(ids, fids, op, acc, limit, page);
    }
}