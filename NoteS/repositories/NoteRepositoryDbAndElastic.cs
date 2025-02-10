using Elastic.Clients.Elasticsearch;
using Microsoft.EntityFrameworkCore;
using NoteS.models.dto;
using NoteS.models.entity;

namespace NoteS.repositories;

public partial class NoteRepositoryDbAndElastic(
    ElasticsearchClient client,
    DbContextOptions<NoteRepositoryDbAndElastic> options) : DbContext(options), INoteRepository
{
    public partial Note Save(Note note);

    public async Task<bool> Delete(Note note)
    {
        return
            DeleteInDb(note) &&
            await DeleteInElastic(note);
        //Нет транзакции, замечательно прост. В теоооории, можно все завернуть в транзакцию бд и тип та, будет ждать подтверждения от эластика. Впрочем врядли это все когда-нибудь вообще стрельнет.
    }

    private partial Task<bool> DeleteInElastic(Note note);
    private partial bool DeleteInDb(Note note);

    public partial Task<Note> SaveContent(Note note);

    public partial Note? FindByPath(Field<INotePath, string> path);

    public partial Task<Note> LoadContent(Note note);

    private partial List<Note> LoadContent(List<Note> notes);

    public partial Note LoadTags(Note note);

    public partial bool DeleteTag(Note note, Tag tag);

    public partial NoteTag AddTag(Note note, Tag tag);

    public partial Task<List<Note>> FindByTitle(Field<INoteTitle, string> title, Field<IAccId, int> ownerId);

    public partial bool IsTagExists(Field<ITagId, int> tag, Note note);

    public List<Note> LoadComments(Note note)
    {
        return LoadContent(LoadCommentsInDb(note));
    }

    private partial List<Note> LoadCommentsInDb(Note note);

    public partial List<Note> FindByTag(Field<ITagId, int> tag, Account owner);

    public partial List<Note> FindByOwner(Account owner);

    public partial Task<List<Note>> SemanticFind(SemanticSearchQuery find, Field<IAccId, int> ownerId);

    public partial Task<Note> CreateInElastic(NoteCreateRequestDto requestDto, Field<IAccId, int> owner);
}