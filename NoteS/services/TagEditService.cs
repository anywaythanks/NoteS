using NoteS.exceptions;
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
        if (noteRepository.IsTagExists(tag, note)) throw new AlreadyExists("Тег у заметки");
        return noteRepository.AddTag(note, tagInformationService.Get(accountName, tag));
    }

    public Tag Create(string accountName, string tag)
    {
        var acc = accountInformationService.Get(accountName);
        if (tagRepository.FindByName(tag, acc) != null) throw new AlreadyExists("Тег");
        var tagNew = new Tag(tag, acc);
        return tagRepository.Save(tagNew);
    }
}