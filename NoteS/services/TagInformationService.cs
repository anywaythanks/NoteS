using NoteS.exceptions;
using NoteS.models.entity;
using NoteS.models.mappers;
using NoteS.repositories;

namespace NoteS.services;

public class TagInformationService(
    AccountInformationService accountInformationService,
    NoteInformationService noteInformationService,
    INoteRepository noteRepository,
    ITagRepository tagRepository,
    TagMapper tm)
{
    public List<Tag> Tags(Field<IAccName, string> accountName)
    {
        return tagRepository.FindByOwner(accountInformationService.Get(accountName));
    }

    public List<Tag> Tags(Field<INotePath, string> pathNote, Field<IAccName, string> accountName)
    {
        var note = noteInformationService.Get(pathNote, accountName);
        noteRepository.LoadTags(note);
        return note.Tags.Select(n => n.Tag).ToList();
    }

    public Tag Get(Field<IAccName, string> accountName, Field<ITagName, string> tag)
    {
        return tagRepository.FindByName(tag, accountInformationService.Get(accountName)) ?? throw new NotFound("тег");
    }

    public List<Note> FindTag(Field<ITagName, string> tag, Field<IAccName, string> owner)
    {
        var acc = accountInformationService.Get(owner);
        var tagI = Get(owner, tag);
        return noteRepository.FindByTag(tm.ToId(tagI), acc);
    }
}