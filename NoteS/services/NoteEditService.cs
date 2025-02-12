using NoteS.exceptions;
using NoteS.models.dto;
using NoteS.models.entity;
using NoteS.models.mappers;
using NoteS.repositories;

namespace NoteS.services;

public class NoteEditService(
    NoteInformationService noteInformationService,
    INoteRepository repository,
    AccountInformationService accountInformationService)
{
    public Note PublishNote(NotePathDto pathNote, AccNameDto owner,
        NoteEditPublicRequestDto requestDto)
    {
        var note = noteInformationService.Get(pathNote, owner);
        note.IsPublic = requestDto.IsPublic;
        return repository.Save(note);
    }

    public async Task Delete(NotePathDto pathNote, AccNameDto owner)
    {
        var note = noteInformationService.Get(pathNote, owner);
        if (!await repository.Delete(note)) throw new DontDel("Заметка");
    }

    public Note PublishNote(NotePathDto pathNote, NoteEditPublicRequestDto requestDto)
    {
        var note = noteInformationService.Get(pathNote);
        note.IsPublic = requestDto.IsPublic;
        return repository.Save(note);
    }

    public Task<Note> EditNote(NotePathDto pathNote, AccNameDto owner,
        NoteEditContentRequestDto requestDto)
    {
        var note = noteInformationService.Get(pathNote, owner);
        if (note.Type != NoteTypes.Note) throw new NoteTypeException("заметка");
        note.Content = requestDto.Content;
        note.SyntaxType = requestDto.Type;
        return repository.SaveContent(note);
    }

    public Task<Note> EditNote(NotePathDto pathNote, NoteEditContentRequestDto requestDto)
    {
        var note = noteInformationService.Get(pathNote);
        if (note.Type != NoteTypes.Note) throw new NoteTypeException("заметка");
        note.Content = requestDto.Content;
        note.SyntaxType = requestDto.Type;
        return repository.SaveContent(note);
    }

    public async Task<Note> CreateNote(AccNameDto accountName, NoteCreateRequestDto requestDto)
    {
        AccIdDto account = accountInformationService.Get(accountName);
        var note = await repository.CreateInElastic(requestDto, account);
        note.Title = requestDto.Title;
        note.Type = NoteTypes.Note;
        note.Path = Guid.NewGuid().ToString();
        note.Owner = account.Id;
        note.SyntaxType = requestDto.Type;
        return repository.Save(note);
    }
}