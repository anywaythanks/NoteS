using NoteS.exceptions;
using NoteS.models.dto;
using NoteS.models.entity;
using NoteS.repositories;

namespace NoteS.services;

public class NoteInformationService(
    INoteRepository repository,
    ITagRepository tagRepository,
    AccountInformationService informationService)
{
    public Task<List<Note>> Find(Field<INoteTitle, string> title, Field<IAccName, string> owner)
    {
        return repository.FindByTitle(title, informationService.Get(owner).Id ?? throw new NotFound("Заметка"));
    }

    public List<Note> FindTag(Field<ITagName, string> tag, Field<IAccName, string> owner)
    {
        var acc = informationService.Get(owner);
        var tagI = tagRepository.FindByName(tag, acc) ?? throw new NotFound("Тег"); 
        return repository.FindByTag(tagI, acc);
    }

    public List<Note> Find(Field<IAccName, string> owner)
    {
        return repository.FindByOwner(informationService.Get(owner));
    }

    public Task<List<Note>> FindSemantic(Field<IAccName, string> owner, SemanticSearchQuery query)
    {
        return repository.SemanticFind(query, informationService.Get(owner).Id ?? throw new NotFound("Заметка"));
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