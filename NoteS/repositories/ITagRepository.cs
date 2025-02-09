using NoteS.models.entity;

namespace NoteS.repositories;

public interface ITagRepository
{
    public Tag Save(Tag tag);
    public List<Tag> FindByOwner(Account owner);
    public Tag? FindByName(Field<ITagName, string> name, Account owner);
}