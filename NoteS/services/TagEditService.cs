using NoteS.Models;
using NoteS.repositories;

namespace NoteS.services;

public class TagEditService(
    AccountInformationService accountInformationService,
    TagInformationService tagInformationService,
    NoteInformationService noteInformationService,
    ITagRepository tagRepository,
    INoteRepository noteRepository)
{
    public bool Delete(string pathNote, string accountName, string tag)
    {
        var note = noteInformationService.Get(pathNote, accountName);
        return noteRepository.DeleteTag(note, tagInformationService.Get(accountName, tag));
    }

    public Tag Add(string pathNote, string accountName, string tag)
    {
        var note = noteInformationService.Get(pathNote, accountName);
        return noteRepository.AddTag(note, tagInformationService.Get(accountName, tag));
    }

    public Tag Create(string accountName, string tag)
    {
        var acc = accountInformationService.Get(accountName);
        var tagNew = new Tag(tag, acc);
        return tagRepository.Save(tagNew);
    }
}