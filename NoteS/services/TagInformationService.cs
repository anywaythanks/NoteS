using NoteS.exceptions;
using NoteS.Models;
using NoteS.repositories;

namespace NoteS.services;

public class TagInformationService(
    AccountInformationService accountInformationService,
    NoteInformationService noteInformationService,
    INoteRepository noteRepository,
    ITagRepository tagRepository)
{
    public List<Tag> Tags(string accountName)
    {
        return tagRepository.FindByOwner(accountInformationService.Get(accountName));
    }

    public List<Tag> Tags(string pathNote, string accountName)
    {
        var note = noteInformationService.Get(pathNote, accountName);
        noteRepository.LoadTags(note);
        return note.Tags.Select(n => n.Tag).ToList();
    }

    public Tag Get(string accountName, string tag)
    {
        return tagRepository.FindByName(tag, accountInformationService.Get(accountName)) ?? throw new NotFound("тег");
    }
}