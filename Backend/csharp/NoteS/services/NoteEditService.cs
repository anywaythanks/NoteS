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
        var note = noteInformationService.Get(pathNote);
        Note? result = null;
        if (NoteTypes.IsComment(note.Type))
        {
            if (note.MainNote != null)
            {
                result = repository.FindById(new NoteIdDto((int)note.MainNote));
                if (result != null && result.Type == NoteTypes.Note)
                {
                    var acc = accountInformationService.Get(result);
                    if (note.OwnerAccount?.Name != owner.Name &&
                        acc.Name != owner.Name) throw new Forbidden("заметке");
                }
            }
        }

        if (result == null && note.OwnerAccount?.Name != owner.Name) throw new Forbidden("заметке");
        note.IsPublic = requestDto.IsPublic;
        return repository.SavePartial(note);
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
        return repository.SavePartial(note);
    }

    public Note EditNote(NotePathDto pathNote, AccNameDto owner,
        NoteEditOtherRequestDto requestDto)
    {
        var note = noteInformationService.Get(pathNote, owner);
        if (note.Type != NoteTypes.Note) throw new NoteTypeException("заметка");
        note.Description = requestDto.Description;
        note.Title = requestDto.Title;
        return repository.SavePartial(note);
    }

    public Note EditNote(NotePathDto pathNote, NoteEditOtherRequestDto requestDto)
    {
        var note = noteInformationService.Get(pathNote);
        if (note.Type != NoteTypes.Note) throw new NoteTypeException("заметка");
        note.Description = requestDto.Description;
        note.Title = requestDto.Title;
        return repository.SavePartial(note);
    }

    public Task<Note> EditNote(NotePathDto pathNote, AccNameDto owner,
        NoteEditOnlyContentRequestDto requestDto)
    {
        var note = noteInformationService.Get(pathNote, owner);
        if (note.Type != NoteTypes.Note) throw new NoteTypeException("заметка");
        note.Content = requestDto.Content;
        return repository.Save(note);
    }

    public Task<Note> EditNote(NotePathDto pathNote, NoteEditOnlyContentRequestDto requestDto)
    {
        var note = noteInformationService.Get(pathNote);
        if (note.Type != NoteTypes.Note) throw new NoteTypeException("заметка");
        note.Content = requestDto.Content;
        return repository.Save(note);
    }

    public Task<Note> CreateNote(AccNameDto accountName, NoteCreateRequestDto requestDto)
    {
        AccIdDto account = accountInformationService.Get(accountName);
        var note = new Note(requestDto.Title)
        {
            Path = Guid.NewGuid().ToString(),
            Content = requestDto.Content,
            Description = requestDto.Description,
            Owner = account.Id,
            Type = NoteTypes.Note,
            IsPublic = false,
            SyntaxType = requestDto.SyntaxType
        };
        return repository.Save(note);
    }
}