using NoteS.Models;
using NoteS.models.dto;

namespace NoteS.repositories;

public interface INoteRepository
{
    public Note Save(Note note);
    public Note SaveContent(Note note);
    public Note? FindByPath(string path);
    public Note LoadContent(Note note);
    public Note LoadTags(Note note);

    public List<Note> FindByTitle(string title, Account owner);

    public List<Note> FindByTag(string title, Account owner);
    public List<Note> FindByOwner(Account owner);
    public void SaveContent(string content);
    public List<Note> SemanticFind(string find, Account owner);
    public Note CreateInElastic(NoteCreateRequestDto requestDto, Account owner);
}