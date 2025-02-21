using Elastic.Clients.Elasticsearch;
using Microsoft.EntityFrameworkCore;
using NoteS.configs;
using NoteS.models.dto;
using NoteS.models.entity;
using NoteS.models.mappers;

namespace NoteS.repositories;

public partial class NoteRepositoryDbAndElastic(
    ElasticsearchClient client,
    DbContextOptions<NoteRepositoryDbAndElastic> options,
    ElasticData elasticData) : DbContext(options), INoteRepository
{
    public async Task<Note> Save(Note note)
    {
        return SavePartial(await SaveInElastic(note));
    }

    public partial Note SavePartial(Note note);

    public async Task<bool> Delete(Note note)
    {
        return
            DeleteInDb(note) &&
            await DeleteInElastic(note);
        //Нет транзакции, замечательно прост. В теоооории, можно все завернуть в транзакцию бд и тип та, будет ждать подтверждения от эластика. Впрочем врядли это все когда-нибудь вообще стрельнет.
    }

    private partial Task<bool> DeleteInElastic(Note note);
    private partial bool DeleteInDb(Note note);

    private partial Task<Note> SaveInElastic(Note note);

    public partial Note? FindByPath(NotePathDto path);

    public partial Task<NoteContentDto?> GetContent(NoteElasticDto note);

    private partial Task<PageDto<Note>> LoadContentElastic(PageDto<Note> notes);
    private partial PageDto<Note> LoadNoteDb(PageDto<SearchResultDto> notes, PageSizeDto page, LimitDto limit);
    public partial PageDto<Note> LoadTags(PageDto<Note> notes);
    public partial List<Tag> GetTags(NoteIdDto note);

    public partial bool DeleteTag(NoteIdDto note, TagIdDto tag);

    public partial bool AddTag(NoteIdDto note, TagIdDto tag);
    public partial Note? FindById(NoteIdDto note);

    public async Task<PageDto<Note>> FindByTitle(NoteTitleDto title, AccIdDto ownerId, PageSizeDto page,
        LimitDto limit)
    {
        return LoadTags(LoadNoteDb(await FindByTitleElastic(title, ownerId, page, limit), page, limit));
    }

    public partial Task<PageDto<SearchResultDto>> FindByTitleElastic(NoteTitleDto title, AccIdDto ownerId,
        PageSizeDto page,
        LimitDto limit);

    public partial bool IsTagExists(TagIdDto tag, NoteIdDto note);

    public Task<PageDto<Note>> GetComments(NoteIdDto note, PageSizeDto pageSize, LimitDto limit)
    {
        return LoadContentElastic(GetCommentsInDb(note, pageSize, limit));
    }

    private partial PageDto<Note> GetCommentsInDb(NoteIdDto note, PageSizeDto pageSize, LimitDto limit);

    public partial PageDto<Note> FindNotesByTags(List<TagIdDto> tags, List<TagIdDto> filterTags, bool op,
        AccIdDto owner, LimitDto limit1, PageSizeDto page1);

    public partial PageDto<Note> FindNotesByOwner(AccIdDto owner, PageSizeDto pageSize, LimitDto limit);

    public async Task<PageDto<Note>> SemanticFind(SemanticSearchQuery find, AccIdDto ownerId, PageSizeDto page,
        LimitDto limit)
    {
        return LoadTags(LoadNoteDb(await SemanticFindInElastic(find, ownerId, page, limit), page, limit));
    }

    private partial Task<PageDto<SearchResultDto>> SemanticFindInElastic(SemanticSearchQuery find, AccIdDto ownerId,
        PageSizeDto page,
        LimitDto limit);

    private partial Task<NoteElasticDto> CreateInElastic(Note note, AccIdDto owner);
}