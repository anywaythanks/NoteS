using NoteS.models.entity;

namespace NoteS.repositories;

public interface ITagRepository
{
    public Tag Save(Tag tag);
    public List<Tag> FindByOwner(AccIdDto owner);
    public Tag? FindByName(TagNameDto name, AccIdDto owner);

    public List<TagIdDto> Tags(List<TagNameDto> tags, AccIdDto owner);
}