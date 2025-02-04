using NoteS.Models;
using NoteS.repositories;

namespace NoteS.services;

public class CommentInformationService(
    INoteRepository repository,
    AccountInformationService informationService)
{
    public List<Note> Comments(string title, string owner)
    {
        return repository.LoadComments(title, informationService.Get(owner));
    }
}