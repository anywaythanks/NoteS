using NoteS.Models;
using NoteS.models.dto;

namespace NoteS.repositories;

public interface INoteRepository
{
    public Note Save(Note note);
    public bool Delete(Note note);
    public Note SaveContent(Note note);
    public Note? FindByPath(string path);
    public Note LoadContent(Note note);
    public Note LoadTags(Note note);

    public bool DeleteTag(Note note, Tag tag);
    public Tag AddTag(Note note, Tag tag);
    public List<Note> FindByTitle(string title, Account owner);

    /**
     * todo: Должно быть с контентом, по идее целой пачкой надо отправлять в elastic
     */
    public List<Note> LoadComments(string path, Account owner);

    public List<Note> FindByTag(string title, Account owner);
    public List<Note> FindByOwner(Account owner);
    public void SaveContent(string content);
    public List<Note> SemanticFind(string find, Account owner);
    public Note CreateInElastic(NoteCreateRequestDto requestDto, Account owner);
}