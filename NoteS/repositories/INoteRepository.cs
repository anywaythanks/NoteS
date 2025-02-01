using NoteS.Models;

namespace NoteS.repositories;

public interface INoteRepository
{
    public Note Save(Note note);
    public Note? FindByTitle(string title);
    public Note? FindByPath(string path);
    public Note Detach(Note note);
}