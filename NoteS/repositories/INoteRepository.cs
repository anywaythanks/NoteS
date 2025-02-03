using NoteS.Models;

namespace NoteS.repositories;

public interface INoteRepository
{
    public Note Save(Note note);
    public Note? FindByPath(string path);
    public Note LoadTags(Note note);
    
    public List<Note> FindByTitle(string title);
    public void SaveContent(string content);
    public List<Note> SemanticFind(string find); 

}