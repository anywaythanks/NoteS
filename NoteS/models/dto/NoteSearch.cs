using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;
using Microsoft.AspNetCore.Mvc;
using NoteS.models.entity;
using static NoteS.models.mappers.MappersInstances;

namespace NoteS.models.dto;

/**
 * Упрощенная заметка, без данных, онли тайтл, путь и тп. Оптимизрованная для поиска.
 * Хотя мейби и включить её, укорачивая контент(Но стоит ли оно того?)
 */
public class NoteSearchResponseDto
{
    public string Title { get; set; }
    public string Path { get; set; }
    [JsonIgnore] public NoteTypes Type { get; set; }
    public decimal Score { get; set; }

    [JsonPropertyName("note_type")]
    [Required]
    [EnumDataType(typeof(NoteTypes.NoteTypeName))]
    public NoteTypes.NoteTypeName NoteTypeName
    {
        get => Type.Name;
        set => Type = NoteTypes.NameToType(value);
    }

    [JsonPropertyName("is_public")] public bool IsPublic { get; set; }

    public DateTime? CreatedOn { get; set; }
}

/**
 * По идее фуловая заметка, с автором и пр. доступная по ссылке
 */
public class NoteSearchContentResponseDto
{
    public string Title { get; set; }
    public string Path { get; set; }

    [JsonPropertyName("owner_account_name")]
    public string OwnerName { get; set; }

    [JsonPropertyName("main_note_path")] public string? MainPath { get; set; }
    public string Content { get; set; }
    public decimal Score { get; set; }
    [JsonIgnore] public NoteTypes Type { get; set; }

    [JsonPropertyName("note_type")]
    [Required]
    [EnumDataType(typeof(NoteTypes.NoteTypeName))]
    public NoteTypes.NoteTypeName NoteTypeName
    {
        get => Type.Name;
        set => Type = NoteTypes.NameToType(value);
    }

    public List<TagResponseDto> Tags { get; set; }
    [JsonPropertyName("is_public")] public bool IsPublic { get; set; }
    public DateTime? CreatedOn { get; set; }
}

public class NoteSearchRequestDto
{
    public string Title { get; set; }
    public static implicit operator NoteTitleDto(NoteSearchRequestDto path) => nm.Of(path);
}

public class NoteSearchTagsRequestDto
{
    [FromQuery] public string Tag { get; set; }
}

public class NoteSemanticSearchRequestDto
{
    public string Query { get; set; }
    public static implicit operator SemanticSearchQuery(NoteSemanticSearchRequestDto name) => um.Of(name);
}

public class NotePath
{
    [FromRoute(Name = "pathNote")]
    [MinLength(3)]
    [MaxLength(128)]
    public string PathNote { get; set; }

    public static implicit operator NotePathDto(NotePath path) => nm.ToPathDto(path);
}
public class AccName
{
    [FromRoute(Name = "accountName")]
    [MaxLength(128)]
    public string AccountName { get; set; }

    public static implicit operator AccNameDto(AccName name) => am.ToNameDto(name);
}
class ElasticRequestDto
{
    public string title { get; init; }
    public string content { get; init; }
    public string syntax_type { get; init; }
}