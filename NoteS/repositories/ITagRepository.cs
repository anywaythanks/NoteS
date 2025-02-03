using NoteS.Models;

namespace NoteS.repositories;

public interface ITagRepository
{
    public Tag Save(Tag tag);
    public List<Note> FindNotes(Tag tag);
    public Tag FindByName(string name);
}