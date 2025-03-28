using System.ComponentModel.DataAnnotations;
using Elastic.Clients.Elasticsearch;
using Microsoft.AspNetCore.Mvc;
using NoteS.models.entity;
using NoteS.models.mappers;

namespace NoteS.models.dto;

using static MappersInstances;

public class TagResponseDto
{
    public string Name { get; set; }
    [Range(0, 0xFFFFFF)] public int Color { get; set; }
}

public class CreateTagRequestDto
{
    public string Name { get; set; }
    [Range(0, 0xFFFFFF)] public int Color { get; set; }
    public static implicit operator TagNameDto(CreateTagRequestDto tag) => tm.OfCreate(tag);
}
public class AddTagRequestDto
{
    public string Name { get; set; }
    public static implicit operator TagNameDto(AddTagRequestDto tag) => tm.OfAdd(tag);
}
public class TagNameRequestDto
{
    [FromRoute(Name = "tagName")]
    [MaxLength(128)]
    public string Name { get; set; }
    public static implicit operator TagNameDto(TagNameRequestDto tagName) => tm.OfDelete(tagName);
}