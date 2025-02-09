using NoteS.Models;
using NoteS.models.dto;

namespace NoteS.repositories;

public interface INoteRepository
{
    public Note Save(Note note);
    public Task<bool> Delete(Note note);
    public Task<Note> SaveContent(Note note);
    public Note? FindByPath(string path);
    public Task<Note> LoadContent(Note note);
    public Note LoadTags(Note note);

    public bool DeleteTag(Note note, Tag tag);
    public Tag AddTag(Note note, Tag tag);
    public Task<List<Note>> FindByTitle(string title, int ownerId);
    public bool IsTagExists(string name, Note note);

    /**
     * todo: Должно быть с контентом, по идее целой пачкой надо отправлять в elastic
     */
    public List<Note> LoadComments(string path, Account owner);

    public List<Note> FindByTag(string title, Account owner);
    public List<Note> FindByOwner(Account owner);
    public Task<List<Note>> SemanticFind(string find, int ownerId);
    public Task<Note> CreateInElastic(NoteCreateRequestDto requestDto, Account owner);
}