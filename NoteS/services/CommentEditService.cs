using NoteS.exceptions;
using NoteS.models;
using NoteS.Models;
using NoteS.models.dto;
using NoteS.repositories;

namespace NoteS.services;

public class CommentEditService(
    NoteInformationService noteInformationService,
    INoteRepository repository,
    AccountInformationService accountInformationService)
{
    public Note EditContentComment(string pathComment, string owner, CommentEditRequestDto requestDto)
    {
        var comment = noteInformationService.Get(pathComment, owner);
        if (!comment.IsEditable()) throw new TimeMissed();
        comment.SyntaxType = requestDto.Type;
        comment.Content = requestDto.Content;
        comment.Title = requestDto.Title;
        return repository.Save(repository.SaveContent(comment));
    }

    public Note EditContentComment(string pathComment, CommentEditRequestDto requestDto)
    {
        var comment = noteInformationService.Get(pathComment);
        comment.SyntaxType = requestDto.Type;
        comment.Content = requestDto.Content;
        comment.Title = requestDto.Title;
        return repository.Save(repository.SaveContent(comment));
    }

    public Note CreateComment(string accountName, string pathNote, CommentCreateRequestDto requestDto)
    {
        var note = noteInformationService.Get(pathNote);
        var account = accountInformationService.Get(accountName);
        var comment = repository.CreateInElastic(requestDto, account);
        comment.Title = requestDto.Title;
        comment.Type = NoteTypes.Comment;
        comment.MainNote = note;
        comment.Path = Guid.NewGuid().ToString();
        comment.Owner = account;
        comment.SyntaxType = requestDto.Type;
        return repository.Save(comment);
    }
}