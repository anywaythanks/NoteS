using NoteS.models.entity;
using NoteS.models.mappers;

namespace NoteS.models.dto;

using static MappersInstances;

public class TagResponseDto
{
    public string Name { get; set; }
}

public class CreateTagRequestDto
{
    public string Name { get; set; }
    public static implicit operator TagNameDto(CreateTagRequestDto tag) => tm.OfCreate(tag);
}

public class DeleteTagRequestDto
{
    public string Name { get; set; }
    public static implicit operator TagNameDto(DeleteTagRequestDto tag) => tm.OfDelete(tag);
}