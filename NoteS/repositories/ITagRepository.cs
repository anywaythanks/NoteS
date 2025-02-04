using NoteS.Models;

namespace NoteS.repositories;

public interface ITagRepository
{
    public Tag Save(Tag tag);
    public bool Delete(Tag note);
    public List<Note> FindNotes(Tag tag);
    public List<Tag> FindByOwner(Account owner);
    public Tag? FindByName(string name, Account owner);
}