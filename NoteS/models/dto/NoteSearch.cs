using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;
using Microsoft.AspNetCore.Mvc;
using NoteS.models.entity;
using Swashbuckle.AspNetCore.Annotations;

namespace NoteS.models.dto;

public class NoteSearchResponseDto
{
    public string Title { get; set; }
}

public class NoteSearchContentResponseDto
{
    public string Title { get; set; }
    public string Content { get; set; }

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
}

public class NoteSearchRequestDto
{
    public string Title { get; set; }
}

public class NoteSearchTagsRequestDto
{
    public string Tag { get; set; }
}

public class NoteSemanticSearchRequestDto
{
    public string Query { get; set; }
}

public class NotePath
{
    [FromRoute(Name="pathNote")]
    [MinLength(3)]
    [MaxLength(128)]
    public string PathNote { get; set; }
}

public class AccName
{
    [FromRoute(Name="accountName")]
    [MaxLength(128)]
    public string AccountName { get; set; }
}