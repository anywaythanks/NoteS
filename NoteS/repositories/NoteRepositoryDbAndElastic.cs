using Elastic.Clients.Elasticsearch;
using Microsoft.EntityFrameworkCore;
using NoteS.Models;
using NoteS.models.dto;

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

    public partial Note? FindByPath(string path);

    public partial Task<Note> LoadContent(Note note);

    public partial Note LoadTags(Note note);

    public partial bool DeleteTag(Note note, Tag tag);

    public partial Tag AddTag(Note note, Tag tag);

    public partial Task<List<Note>> FindByTitle(string title, int ownerId);

    public partial bool IsTagExists(string name, Note note);

    public partial List<Note> LoadComments(string path, Account owner);

    public partial List<Note> FindByTag(string title, Account owner);

    public partial Task<List<Note>> FindByOwner(Account owner);

    public partial Task<List<Note>> SemanticFind(string find, int ownerId);

    public partial Task<Note> CreateInElastic(NoteCreateRequestDto requestDto, Account owner);
}