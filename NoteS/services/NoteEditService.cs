using NoteS.models.dto;
using NoteS.models.entity;
using NoteS.models.mappers;
using NoteS.repositories;

namespace NoteS.services;

public class NoteEditService(
    NoteInformationService noteInformationService,
    INoteRepository repository,
    AccountInformationService accountInformationService,
    AccountMapper am)
{
    public Note PublishNote(Field<INotePath, string> pathNote, Field<IAccName, string> owner,
        NoteEditPublicRequestDto requestDto)
    {
        var note = noteInformationService.Get(pathNote, owner);
        note.IsPublic = requestDto.IsPublic;
        return repository.Save(note);
    }

    public Task<bool> Delete(Field<INotePath, string> pathNote, Field<IAccName, string> owner)
    {
        var note = noteInformationService.Get(pathNote, owner);
        return repository.Delete(note);
    }

    public Note PublishNote(Field<INotePath, string> pathNote, NoteEditPublicRequestDto requestDto)
    {
        var note = noteInformationService.Get(pathNote);
        note.IsPublic = requestDto.IsPublic;
        return repository.Save(note);
    }

    public Task<Note> EditContentNote(Field<INotePath, string> pathNote, Field<IAccName, string> owner,
        NoteEditContentRequestDto requestDto)
    {
        var note = noteInformationService.Get(pathNote, owner);
        note.Content = requestDto.Content;
        return repository.SaveContent(note);
    }

    public Task<Note> EditContentNote(Field<INotePath, string> pathNote, NoteEditContentRequestDto requestDto)
    {
        var note = noteInformationService.Get(pathNote);
        note.Content = requestDto.Content;
        return repository.SaveContent(note);
    }

    public async Task<Note> CreateNote(Field<IAccName, string> accountName, NoteCreateRequestDto requestDto)
    {
        var account = am.ToId(accountInformationService.Get(accountName));
        var note = await repository.CreateInElastic(requestDto, account);
        note.Title = requestDto.Title;
        note.Type = NoteTypes.Note;
        note.Path = Guid.NewGuid().ToString();
        note.Owner = account.Val;
        note.SyntaxType = requestDto.Type;
        return repository.Save(note);
    }
}