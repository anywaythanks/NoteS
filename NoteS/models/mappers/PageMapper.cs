using NoteS.models.dto;
using NoteS.models.entity;
using Riok.Mapperly.Abstractions;

namespace NoteS.models.mappers;

[Mapper(RequiredMappingStrategy = RequiredMappingStrategy.Target,
    EnabledConversions = MappingConversionType.Constructor)]
public partial class PageMapper
{
    public partial PageSizeDto OfPage(PaginationRequestDto prd);
    public partial LimitDto OfLimit(PaginationRequestDto prd);
}