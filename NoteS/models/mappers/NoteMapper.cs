using NoteS.models.dto;
using NoteS.models.entity;
using Riok.Mapperly.Abstractions;

namespace NoteS.models.mappers;

[Mapper(RequiredMappingStrategy = RequiredMappingStrategy.Target,
    EnabledConversions = MappingConversionType.Constructor)]
public partial class NoteMapper
{
    public partial NoteIdDto ToIdDto(Note note);

    [MapProperty("Owner", "Id")]
    public partial AccIdDto ToAccIdDto(Note note);

    public partial NoteTitleDto ToTitleDto(Note note);
    public partial NoteElasticDto ToElasticDto(Note note);
    public partial NotePathDto ToPathDto(Note note);

    [MapProperty("PathNote", "Path")]
    public partial NotePathDto ToPathDto(NotePath path);
    public partial NoteIsPublicDto ToIsPublicDto(Note note);
    public partial NoteContentDto ToContentDto(Note note);
    public partial NoteTitleDto Of(NoteSearchRequestDto note);
}