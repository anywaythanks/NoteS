using Elastic.Clients.Elasticsearch;
using Microsoft.EntityFrameworkCore;
using NoteS.models.dto;
using NoteS.models.entity;
using NoteS.models.mappers;

namespace NoteS.repositories;

public partial class NoteRepositoryDbAndElastic(
    ElasticsearchClient client,
    DbContextOptions<NoteRepositoryDbAndElastic> options,
    NoteMapper nm) : DbContext(options), INoteRepository
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

    public partial Note? FindByPath(NotePathDto path);

    public partial Task<NoteContentDto?> GetContent(NoteElasticDto note);

    private partial PageDto<Note> LoadContent(PageDto<Note> notes);

    public partial List<Tag> GetTags(NoteIdDto note);

    public partial  bool DeleteTag(NoteIdDto note, TagIdDto tag);

    public partial bool AddTag(NoteIdDto note, TagIdDto tag);

    public partial Task<PageDto<Note>> FindByTitle(NoteTitleDto title, AccIdDto ownerId, PageSizeDto page,
        LimitDto limit);

    public partial bool IsTagExists(TagIdDto tag, NoteIdDto note);

    public PageDto<Note> GetComments(NoteIdDto note, PageSizeDto pageSize, LimitDto limit)
    {
        return LoadContent(GetCommentsInDb(note, pageSize, limit));
    }

    private partial PageDto<Note> GetCommentsInDb(NoteIdDto note, PageSizeDto pageSize, LimitDto limit);

    public partial PageDto<Note> FindNotesByTags(List<TagIdDto> tags, List<TagIdDto> filterTags, bool op,
        AccIdDto owner, LimitDto limit1, PageSizeDto page1);

    public partial PageDto<Note> FindNotesByOwner(AccIdDto owner, PageSizeDto pageSize, LimitDto limit);

    public partial Task<PageDto<Note>> SemanticFind(SemanticSearchQuery find, AccIdDto ownerId, PageSizeDto page,
        LimitDto limit);

    public partial Task<Note> CreateInElastic(NoteCreateRequestDto requestDto, AccIdDto owner);
}