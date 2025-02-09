using NoteS.models.entity;
using NoteS.repositories;

namespace NoteS.services;

public class CommentInformationService(
    INoteRepository repository,
    AccountInformationService informationService)
{
    public List<Note> Comments(Field<IAccName, string> name, Field<INotePath, string> path)
    {
        return repository.LoadComments(path, informationService.Get(name));
    }
}