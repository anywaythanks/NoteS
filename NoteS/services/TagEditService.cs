using NoteS.exceptions;
using NoteS.models.entity;
using NoteS.models.mappers;
using NoteS.repositories;

namespace NoteS.services;

public class TagEditService(
    AccountInformationService accountInformationService,
    TagInformationService tagInformationService,
    NoteInformationService noteInformationService,
    ITagRepository tagRepository,
    INoteRepository noteRepository,
    TagMapper tm)
{
    public bool Delete(Field<INotePath, string> pathNote, Field<IAccName, string> accountName,
        Field<ITagName, string> tag)
    {
        var note = noteInformationService.Get(pathNote, accountName);
        return noteRepository.DeleteTag(note, tagInformationService.Get(accountName, tag));
    }

    public Tag Add(Field<INotePath, string> pathNote, Field<IAccName, string> accountName, Field<ITagName, string> tag)
    {
        var note = noteInformationService.Get(pathNote, accountName);
        var tagI = tm.ToId(tagInformationService.Get(accountName, tag));
        if (noteRepository.IsTagExists(tagI, note)) throw new AlreadyExists("Тег у заметки");
        return noteRepository.AddTag(note, tagInformationService.Get(accountName, tag)).Tag;
    }

    public Tag Create(Field<IAccName, string> accountName, Field<ITagName, string> tag)
    {
        var acc = accountInformationService.Get(accountName);
        if (tagRepository.FindByName(tag, acc) != null) throw new AlreadyExists("Тег");
        var tagNew = new Tag(tag.Val) { Owner = acc };
        return tagRepository.Save(tagNew);
    }
}