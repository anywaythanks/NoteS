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
        var tagI = tagInformationService.Get(accountName, tag);
        if (noteRepository.IsTagExists(tagI, note)) throw new AlreadyExists("Тег у заметки");
        return noteRepository.AddTag(note, tagInformationService.Get(accountName, tag)).Tag;
    }

    public Tag Create(string accountName, string tag)
    {
        var acc = accountInformationService.Get(accountName);
        if (tagRepository.FindByName(tag, acc) != null) throw new AlreadyExists("Тег");
        var tagNew = new Tag(tag) {Owner = acc};
        return tagRepository.Save(tagNew);
    }
}