using NoteS.models.dto;
using NoteS.models.mappers;

namespace NoteS.models.entity;

using static MappersInstances;

public partial class Tag
{
    public List<NoteTag> Notes { get; init; } = []; //many-to-many
    public static implicit operator TagIdDto(Tag tag) => tm.ToIdDto(tag); 
    public static implicit operator TagNameDto(Tag tag) => tm.ToNameDto(tag);
    public static implicit operator TagResponseDto(Tag tag) => um.Of(tag);
}

public readonly struct TagIdDto(int id)
{
    public int Id { get; init; } = id;
}

public readonly struct TagNameDto(string name)
{
    public string Name { get; init; } = name;
}