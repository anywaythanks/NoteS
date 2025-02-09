using NoteS.models.dto;
using NoteS.models.entity;

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

    public partial Note? FindByPath(Field<INotePath, string> path);

    public partial Note LoadContent(Note note);

    public partial Note LoadTags(Note note);

    public partial bool DeleteTag(Note note, Tag tag);

    public partial Tag AddTag(Note note, Tag tag);

    public partial List<Note> FindByTitle(Field<INoteTitle, string> title, Account owner);

    public partial bool IsTagExists(Field<ITagName, string>  name, Note note);

    public partial List<Note> LoadComments(Field<INotePath, string> path, Account owner);

    public partial List<Note> FindByTag(Field<ITagName, string> tag, Account owner);

    public partial List<Note> FindByOwner(Account owner);

    public partial void SaveContent(Field<INoteContent, string> content);

    public partial List<Note> SemanticFind(SemanticSearchQuery find, Account owner);

    public partial Note CreateInElastic(NoteCreateRequestDto requestDto, Account owner);
}