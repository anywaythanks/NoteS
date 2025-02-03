﻿using NoteS.Models;
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

    public Note PublishNote(string pathNote, NoteEditPublicRequestDto requestDto)
    {
        var note = noteInformationService.Get(pathNote);
        note.IsPublic = requestDto.IsPublic;
        return repository.Save(note);
    }

    public Note EditContentNote(string pathNote, string owner, NoteEditContentRequestDto requestDto)
    {
        var note = noteInformationService.Get(pathNote, owner);
        note.Content = requestDto.Content;
        return repository.SaveContent(note);
    }

    public Note EditContentNote(string pathNote, NoteEditContentRequestDto requestDto)
    {
        var note = noteInformationService.Get(pathNote);
        note.Content = requestDto.Content;
        return repository.SaveContent(note);
    }

    public Note CreateNote(string accountName, NoteCreateRequestDto requestDto)
    {
        var account = accountInformationService.Get(accountName);
        var note = repository.CreateInElastic(requestDto, account);
        note.Title = requestDto.Title;
        note.Path = Guid.NewGuid().ToString();
        note.Owner = account;
        note.SyntaxType = requestDto.Type;
        return repository.Save(note);
    }
}