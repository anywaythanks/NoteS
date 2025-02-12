using NoteS.models.dto;
using NoteS.models.entity;

namespace NoteS.repositories;

public interface INoteRepository
{
    public Note Save(Note note);
    public Task<bool> Delete(Note note);
    public Task<Note> SaveContent(Note note);
    public Note? FindByPath(NotePathDto path);
    public Task<NoteContentDto?> GetContent(NoteElasticDto note);
    public List<Tag> GetTags(NoteIdDto note);
    public bool DeleteTag(NoteIdDto note, TagIdDto tag);
    public bool AddTag(NoteIdDto note, TagIdDto tag);

    public Task<PageDto<Note>> FindByTitle(NoteTitleDto title, AccIdDto ownerId, PageSizeDto page,
        LimitDto limit);

    public bool IsTagExists(TagIdDto tag, NoteIdDto note);

    /**
     * todo: Должно быть с контентом, по идее целой пачкой надо отправлять в elastic
     */
    public PageDto<Note> GetComments(NoteIdDto note, PageSizeDto pageSize, LimitDto limit);

    public PageDto<Note> FindNotesByTags(List<TagIdDto> tags, List<TagIdDto> filterTags, bool op,
        AccIdDto owner,  LimitDto limit, PageSizeDto page);

    public PageDto<Note> FindNotesByOwner(AccIdDto owner, PageSizeDto pageSize, LimitDto limit);

    public Task<PageDto<Note>> SemanticFind(SemanticSearchQuery find, AccIdDto ownerId, PageSizeDto page,
        LimitDto limit);

    public Task<Note> CreateInElastic(NoteCreateRequestDto requestDto, AccIdDto owner);
}