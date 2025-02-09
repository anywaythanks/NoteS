using NoteS.models.dto;
using NoteS.models.entity;

namespace NoteS.repositories;

public interface INoteRepository
{
    public Note Save(Note note);
    public bool Delete(Note note);
    public Note SaveContent(Note note);
    public Note? FindByPath(Field<INotePath, string> path);
    public Note LoadContent(Note note);
    public Note LoadTags(Note note);

    public bool DeleteTag(Note note, Tag tag);
    public Tag AddTag(Note note, Tag tag);
    public List<Note> FindByTitle(Field<INoteTitle, string> title, Account owner);
    public bool IsTagExists(Field<ITagName, string> name, Note note);

    /**
     * todo: Должно быть с контентом, по идее целой пачкой надо отправлять в elastic
     */
    public List<Note> LoadComments(Field<INotePath, string> path, Account owner);

    public List<Note> FindByTag(Field<ITagName, string> tag, Account owner);
    public List<Note> FindByOwner(Account owner);
    public void SaveContent(Field<INoteContent, string> content);
    public List<Note> SemanticFind(SemanticSearchQuery find, Account owner);
    public Note CreateInElastic(NoteCreateRequestDto requestDto, Account owner);
}