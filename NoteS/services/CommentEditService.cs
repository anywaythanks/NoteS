using NoteS.exceptions;
using NoteS.models.dto;
using NoteS.models.entity;
using NoteS.models.mappers;
using NoteS.repositories;

namespace NoteS.services;

public class CommentEditService(
    NoteInformationService noteInformationService,
    INoteRepository repository,
    AccountInformationService accountInformationService,
    AccountMapper am)
{
    public async Task<Note> EditContentComment(Field<INotePath, string> pathComment, Field<IAccName, string> owner,
        CommentEditRequestDto requestDto)
    {
        var comment = noteInformationService.Get(pathComment, owner);
        if (!comment.IsEditable()) throw new TimeMissed("редактирования комментариев");
        comment.SyntaxType = requestDto.SyntaxType;
        comment.Content = requestDto.Content;
        comment.Title = requestDto.Title;
        return repository.Save(await repository.SaveContent(comment));
    }

    public async Task<Note> EditContentComment(Field<INotePath, string> pathComment, CommentEditRequestDto requestDto)
    {
        var comment = noteInformationService.Get(pathComment);
        comment.SyntaxType = requestDto.SyntaxType;
        comment.Content = requestDto.Content;
        comment.Title = requestDto.Title;
        return repository.Save(await repository.SaveContent(comment));
    }

    public async Task<Note> CreateComment(Field<IAccName, string> accountName, Field<INotePath, string> pathNote,
        CommentCreateRequestDto requestDto)
    {
        var note = noteInformationService.Get(pathNote);
        var account = am.ToId(accountInformationService.Get(accountName));
        var comment = await repository.CreateInElastic(requestDto, account);
        comment.Title = requestDto.Title;
        comment.Type = NoteTypes.Comment;
        comment.MainNote = note.Id;
        comment.Path = Guid.NewGuid().ToString();
        comment.Owner = account.Val;
        comment.SyntaxType = requestDto.Type;
        return repository.Save(comment);
    }

    public Task<bool> Delete(Field<INotePath, string> pathComment, Field<IAccName, string> owner)
    {
        var note = noteInformationService.Get(pathComment, owner);
        if (!note.IsEditable()) throw new TimeMissed("удаления комментариев");
        return repository.Delete(note);
    }
}