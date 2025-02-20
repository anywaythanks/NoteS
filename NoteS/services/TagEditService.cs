using NoteS.exceptions;
using NoteS.models.dto;
using NoteS.models.entity;
using NoteS.models.mappers;
using NoteS.repositories;

namespace NoteS.services;

public class TagEditService(
    AccountInformationService accountInformationService,
    TagInformationService tagInformationService,
    NoteInformationService noteInformationService,
    ITagRepository tagRepository,
    INoteRepository noteRepository)
{
    public void Delete(NotePathDto pathNote, AccNameDto accountName,
        TagNameDto tag)
    {
        var note = noteInformationService.Get(pathNote, accountName);
        var tagI = tagInformationService.Get(accountName, tag);
        if (!noteRepository.DeleteTag(note, tagI)) throw new DontDel("Тег заметки");
    }

    public bool Add(NotePathDto pathNote, AccNameDto accountName, TagNameDto tag)
    {
        var note = noteInformationService.Get(pathNote, accountName);
        var tagI = tagInformationService.Get(accountName, tag);
        if (noteRepository.IsTagExists(tagI, note)) throw new AlreadyExists("Тег у заметки");
        return noteRepository.AddTag(note, tagI);
    }

    public Tag Create(AccName accountName, CreateTagRequestDto tag)
    {
        var acc = accountInformationService.Get(accountName);
        if (tagRepository.FindByName(tag, acc) != null) throw new AlreadyExists("Тег");
        var tagNew = new Tag(tag.Name) { Owner = acc.Id, Color = tag.Color };
        return tagRepository.Save(tagNew);
    }
}