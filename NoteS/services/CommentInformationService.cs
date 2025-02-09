using NoteS.models.entity;
using NoteS.repositories;

namespace NoteS.services;

public class CommentInformationService(
    INoteRepository repository,
    NoteInformationService noteInformationService)
{
    public List<Note> Comments(Field<IAccName, string> name, Field<INotePath, string> path)
    {
        var note = noteInformationService.Get(path, name);
        return repository.LoadComments(note);
    }
}