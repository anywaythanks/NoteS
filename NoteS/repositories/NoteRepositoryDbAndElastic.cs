using NoteS.Models;
using NoteS.models.dto;

namespace NoteS.repositories;

public partial class NoteRepositoryDbAndElastic : INoteRepository
{
    public partial Note Save(Note note);

    public bool Delete(Note note)
    {
        return
            DeleteInDb(note) &&
            DeleteInElastic(note);
        //Нет транзакции, замечательно прост. В теоооории, можно все завернуть в транзакцию бд и тип та, будет ждать подтверждения от эластика. Впрочем врядли это все когда-нибудь вообще стрельнет.
    }

    private partial bool DeleteInElastic(Note note);
    private partial bool DeleteInDb(Note note);

    public partial Note SaveContent(Note note);

    public partial Note? FindByPath(string path);

    public partial Note LoadContent(Note note);

    public partial Note LoadTags(Note note);

    public partial bool DeleteTag(Note note, Tag tag);

    public partial Tag AddTag(Note note, Tag tag);

    public partial List<Note> FindByTitle(string title, Account owner);

    public partial bool IsTagExists(string name, Note note);

    public partial List<Note> LoadComments(string path, Account owner);

    public partial List<Note> FindByTag(string title, Account owner);

    public partial List<Note> FindByOwner(Account owner);

    public partial void SaveContent(string content);

    public partial List<Note> SemanticFind(string find, Account owner);

    public partial Note CreateInElastic(NoteCreateRequestDto requestDto, Account owner);
}