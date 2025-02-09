using NoteS.exceptions;
using NoteS.models.dto;
using NoteS.models.entity;
using NoteS.repositories;

namespace NoteS.services;

public class NoteInformationService(
    INoteRepository repository,
    AccountInformationService informationService)
{
    public List<Note> Find(Field<INoteTitle, string> title, Field<IAccName, string> owner)
    {
        return repository.FindByTitle(title, informationService.Get(owner));
    }

    public List<Note> FindTag(Field<ITagName, string> tag, Field<IAccName, string> owner)
    {
        return repository.FindByTag(tag, informationService.Get(owner));
    }

    public List<Note> Find(Field<IAccName, string> owner)
    {
        return repository.FindByOwner(informationService.Get(owner));
    }

    public List<Note> FindSemantic(Field<IAccName, string> owner, SemanticSearchQuery query)
    {
        return repository.SemanticFind(query, informationService.Get(owner));
    }

    public Note Get(Field<INotePath, string> path, Field<IAccName, string> owner)
    {
        var note = repository.FindByPath(path) ?? throw new NotFound("заметка");
        if (note.Owner.Name != owner.Val) throw new Forbidden("заметке");
        return note;
    }

    public Note Get(Field<INotePath, string> path)
    {
        var note = repository.FindByPath(path) ?? throw new NotFound("заметка");
        return note;
    }

    public Note GetFull(Field<INotePath, string> path, Field<IAccName, string> owner)
    {
        var note = Get(path, owner);
        repository.LoadContent(note);
        repository.LoadTags(note);
        return note;
    }

    public Note GetFull(Field<INotePath, string> path)
    {
        var note = Get(path);
        repository.LoadContent(note);
        repository.LoadTags(note);
        return note;
    }
}