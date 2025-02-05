using NoteS.Models;
using NoteS.repositories;

namespace NoteS.services;

public class CommentInformationService(
    INoteRepository repository,
    NoteInformationService noteInformationService)
{
    public List<Note> Comments(string pathNote, string owner)
    {
        var note = noteInformationService.Get(pathNote, owner);
        return repository.LoadComments(note);
    }
}