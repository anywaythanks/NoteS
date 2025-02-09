using NoteS.models.dto;
using NoteS.models.entity;

namespace NoteS.repositories;

public interface INoteRepository
{
    public Note Save(Note note);
    public Task<bool> Delete(Note note);
    public Task<Note> SaveContent(Note note);
    public Note? FindByPath(Field<INotePath, string> path);
    public Note LoadContent(Note note);
    public Note LoadTags(Note note);

    public bool DeleteTag(Note note, Tag tag);
    public NoteTag AddTag(Note note, Tag tag);
    public Task<List<Note>> FindByTitle(Field<INoteTitle, string> title, Field<IAccId, int> ownerId);
    public bool IsTagExists(Field<ITagName, string> tag, Note note);

    /**
     * todo: Должно быть с контентом, по идее целой пачкой надо отправлять в elastic
     */
    public List<Note> LoadComments(Note note);

    public List<Note> FindByTag(Field<ITagName, string> tag, Account owner);
    public List<Note> FindByOwner(Account owner);
    public Task<List<Note>> SemanticFind(SemanticSearchQuery find, int ownerId);
    public Task<Note> CreateInElastic(NoteCreateRequestDto requestDto, Account owner);
}