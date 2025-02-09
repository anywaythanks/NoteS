using NoteS.models;
using NoteS.Models;
using NoteS.models.dto;
using NoteS.repositories;

namespace NoteS.services;

public class NoteEditService(
    NoteInformationService noteInformationService,
    INoteRepository repository,
    AccountInformationService accountInformationService)
{
    public Note PublishNote(string pathNote, string owner, NoteEditPublicRequestDto requestDto)
    {
        var note = noteInformationService.Get(pathNote, owner);
        note.IsPublic = requestDto.IsPublic;
        return repository.Save(note);
    }

    public Task<bool> Delete(string pathNote, string owner)
    {
        var note = noteInformationService.Get(pathNote, owner);
        return repository.Delete(note);
    }

    public Note PublishNote(string pathNote, NoteEditPublicRequestDto requestDto)
    {
        var note = noteInformationService.Get(pathNote);
        note.IsPublic = requestDto.IsPublic;
        return repository.Save(note);
    }

    public Task<Note> EditContentNote(string pathNote, string owner, NoteEditContentRequestDto requestDto)
    {
        var note = noteInformationService.Get(pathNote, owner);
        note.Content = requestDto.Content;
        return repository.SaveContent(note);
    }

    public Task<Note> EditContentNote(string pathNote, NoteEditContentRequestDto requestDto)
    {
        var note = noteInformationService.Get(pathNote);
        note.Content = requestDto.Content;
        return repository.SaveContent(note);
    }

    public async Task<Note> CreateNote(string accountName, NoteCreateRequestDto requestDto)
    {
        var account = accountInformationService.Get(accountName);
        var note = await repository.CreateInElastic(requestDto, account);
        note.Title = requestDto.Title;
        note.Type = NoteTypes.Note;
        note.Path = Guid.NewGuid().ToString();
        note.Owner = account;
        note.SyntaxType = requestDto.Type;
        return repository.Save(note);
    }
}