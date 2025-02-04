using NoteS.exceptions;
using NoteS.Models;
using NoteS.repositories;

namespace NoteS.services;

public class NoteInformationService(
    INoteRepository repository,
    AccountInformationService informationService)
{
    public List<Note> Find(string title, string owner)
    {
        return repository.FindByTitle(title, informationService.Get(owner));
    }
    public List<Note> FindTag(string tag, string owner)
    {
        return repository.FindByTag(tag, informationService.Get(owner));
    }
    public List<Note> Find(string owner)
    {
        return repository.FindByOwner(informationService.Get(owner));
    }

    public List<Note> FindSemantic(string owner, string query)
    {
        return repository.SemanticFind(query, informationService.Get(owner));
    }

    public Note Get(string path, string owner)
    {
        var note = repository.FindByPath(path) ?? throw new NotFound();
        if (note.Owner.Name != owner) throw new Forbidden();
        return note;
    }
    public Note Get(string path)
    {
        var note = repository.FindByPath(path) ?? throw new NotFound();
        return note;
    }
    public Note GetFull(string path, string owner)
    {
        var note = Get(path, owner);
        repository.LoadContent(note);
        repository.LoadTags(note);
        return note;
    }

    public Note GetFull(string path)
    {
        var note = Get(path);
        repository.LoadContent(note);
        repository.LoadTags(note);
        return note;
    }
}