using NoteS.models.dto;
using NoteS.models.entity;
using NoteS.repositories;

namespace NoteS.services;

public class CommentInformationService(
    INoteRepository repository,
    NoteInformationService noteInformationService)
{
    public PageDto<Note> Comments(AccNameDto name, NotePathDto path, PageSizeDto pageSize, LimitDto limit)
    {
        var note = noteInformationService.GetPublic(path, name);
        var page = repository.GetComments(note, pageSize, limit);
        page.items.ForEach(c => c.MainNoteObject = note);
        return page;
    }
}