using NoteS.exceptions;
using NoteS.models.dto;
using NoteS.models.entity;
using NoteS.models.mappers;
using NoteS.repositories;

namespace NoteS.services;

public class CommentEditService(
    NoteInformationService noteInformationService,
    INoteRepository repository,
    AccountInformationService accountInformationService)
{
    public async Task<Note> EditContentComment(NotePathDto pathComment, AccNameDto owner,
        CommentEditRequestDto requestDto)
    {
        var comment = noteInformationService.Get(pathComment, owner);
        if (comment.Type != NoteTypes.Comment) throw new NoteTypeException("комментарий");
        if (!comment.IsEditable()) throw new TimeMissed("редактирования комментариев");
        comment.SyntaxType = requestDto.SyntaxType;
        comment.Content = requestDto.Content;
        comment.Title = requestDto.Title;
        return repository.Save(await repository.SaveContent(comment));
    }

    public async Task<Note> EditContentComment(NotePathDto pathComment, CommentEditRequestDto requestDto)
    {
        var comment = noteInformationService.Get(pathComment);
        comment.SyntaxType = requestDto.SyntaxType;
        comment.Content = requestDto.Content;
        comment.Title = requestDto.Title;
        return repository.Save(await repository.SaveContent(comment));
    }

    public async Task<Note> CreateComment(AccNameDto accountName, NotePathDto pathNote,
        CommentCreateRequestDto requestDto)
    {
        var note = noteInformationService.Get(pathNote);
        AccIdDto account = accountInformationService.Get(accountName);
        var comment = await repository.CreateInElastic(requestDto, account);
        comment.Title = requestDto.Title;
        comment.Type = NoteTypes.Comment;
        comment.MainNote = note.Id;
        comment.IsPublic = true;
        comment.Path = Guid.NewGuid().ToString();
        comment.Owner = account.Id;
        comment.SyntaxType = requestDto.Type;
        return repository.Save(comment);
    }

    public async Task Delete(NotePathDto pathComment, AccNameDto owner)
    {
        var note = noteInformationService.Get(pathComment, owner);
        if (!note.IsEditable()) throw new TimeMissed("удаления комментариев");
        if (!await repository.Delete(note)) throw new DontDel("Комментарий");
    }
}