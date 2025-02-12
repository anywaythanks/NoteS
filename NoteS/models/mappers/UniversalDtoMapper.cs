using NoteS.models.dto;
using NoteS.models.entity;
using Riok.Mapperly.Abstractions;

namespace NoteS.models.mappers;

[Mapper(RequiredMappingStrategy = RequiredMappingStrategy.Target,
    EnabledConversions = MappingConversionType.Constructor)]
public partial class UniversalDtoMapper
{
    [MapperIgnoreTarget("NoteTypeName")]
    private partial NoteSearchResponseDto OfSearch(Note source);

    private List<NoteSearchResponseDto> OfSearch(List<Note> source) => source.Select(OfSearch).ToList();
    public partial PageDto<NoteSearchResponseDto> Of(PageDto<Note> source);
    public partial PageDto<NoteSearchContentResponseDto> OfContent(PageDto<Note> source);
    public partial PageDto<NoteSearchContentResponseDto> OfPage(PageDto<Note> source);

    [MapperIgnoreTarget("NoteTypeName")]
    [MapProperty("MainNoteObject.Path", "MainPath")]
    [MapProperty("OwnerAccount.Name", "OwnerName")]
    public partial NoteSearchContentResponseDto OfContentSearch(Note source);

    private List<NoteSearchContentResponseDto> OfCommentsSearch(List<Note> source) =>
        source.Select(OfContentSearch).ToList();

    public partial NoteEditPublicResponseDto OfEdit(Note source);

    [MapperIgnoreTarget("TypeName")]
    public partial NoteEditContentResponseDto OfEditContent(Note source);

    [MapperIgnoreTarget("NoteTypeName")]
    [MapperIgnoreTarget("TypeName")]
    public partial CommentEditResponseDto OfEditComment(Note source);

    [MapperIgnoreTarget("NoteTypeName")]
    [MapperIgnoreTarget("TypeName")]
    public partial NoteCreateResponseDto OfCreateNote(Note source);

    [MapperIgnoreTarget("NoteTypeName")]
    [MapperIgnoreTarget("TypeName")]
    public partial CommentCreateResponseDto OfCreateComment(Note source);
    
    [MapperIgnoreTarget("TypeName")]
    public partial NoteCreateRequestDto OfCreateContent(Note source);
    public partial TagResponseDto Of(Tag tag);
    public List<TagResponseDto> Of(List<Tag> tag) => tag.Select(Of).ToList();
    public partial PageDto<TagResponseDto> Of(PageDto<Tag> source);
    public partial SemanticSearchQuery Of(NoteSemanticSearchRequestDto tag);
}