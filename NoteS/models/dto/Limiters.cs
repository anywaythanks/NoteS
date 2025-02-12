using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

namespace NoteS.models.dto;

using static NoteS.models.mappers.MappersInstances;

public readonly struct PageSizeDto(int page)
{
    public int Page { get; init; } = page;
}

public readonly struct LimitDto(int limit)
{
    public int Limit { get; init; } = limit;
}

public class PaginationRequestDto
{
    [Range(0, int.MaxValue)] public int Page { get; set; } = 0;
    [Range(5, 20)] public int Limit { get; set; } = 5;
    public static implicit operator PageSizeDto(PaginationRequestDto prd) => pm.OfPage(prd);
    public static implicit operator LimitDto(PaginationRequestDto prd) => pm.OfLimit(prd);
}

public class PageDto<T>
{
    public required List<T> items { get; init; }
    public required long Total { get; init; }
    public long Page { get; init; }
}