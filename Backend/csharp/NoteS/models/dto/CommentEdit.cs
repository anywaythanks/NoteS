using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;
using NoteS.models.entity;

namespace NoteS.models.dto;

public class CommentCreateRequestDto
    : NoteCreateRequestDto;

public class CommentCreateResponseDto
{
    public string Path { get; set; }
    public string Title { get; set; }
    [JsonIgnore] public NoteTypes Type { get; set; }

    [JsonPropertyName("note_type")]
    [Required]
    [EnumDataType(typeof(NoteTypes.NoteTypeName))]
    public NoteTypes.NoteTypeName NoteTypeName
    {
        get => Type.Name;
        set => Type = NoteTypes.NameToType(value);
    }

    [JsonPropertyName("is_public")] public bool IsPublic { get; set; }
    [JsonIgnore] public SyntaxType SyntaxType { get; set; }

    [JsonPropertyName("syntax_name")]
    [Required]
    [EnumDataType(typeof(SyntaxType.SyntaxTypeName))]
    public SyntaxType.SyntaxTypeName TypeName
    {
        get => SyntaxType.Name;
        set => SyntaxType = SyntaxType.NameToType(value);
    }

    public string Content { get; set; }
}

public class CommentEditRequestDto
{
    public string Title { get; set; }
    [JsonIgnore] public SyntaxType SyntaxType { get; set; }

    [JsonPropertyName("syntax_name")]
    [Required]
    [EnumDataType(typeof(SyntaxType.SyntaxTypeName))]
    public SyntaxType.SyntaxTypeName TypeName
    {
        get => SyntaxType.Name;
        set => SyntaxType = SyntaxType.NameToType(value);
    }

    public string Content { get; set; }
}

public class CommentEditResponseDto
{
    public string Path { get; set; }
    public string Title { get; set; }
    [JsonIgnore] public NoteTypes Type { get; set; }

    [JsonPropertyName("note_type")]
    [Required]
    [EnumDataType(typeof(NoteTypes.NoteTypeName))]
    public NoteTypes.NoteTypeName NoteTypeName
    {
        get => Type.Name;
        set => Type = NoteTypes.NameToType(value);
    }

    [JsonPropertyName("is_public")] public bool IsPublic { get; set; }
    [JsonIgnore] public SyntaxType SyntaxType { get; set; }

    [JsonPropertyName("syntax_name")]
    [Required]
    [EnumDataType(typeof(SyntaxType.SyntaxTypeName))]
    public SyntaxType.SyntaxTypeName TypeName
    {
        get => SyntaxType.Name;
        set => SyntaxType = SyntaxType.NameToType(value);
    }

    public string Content { get; set; }
}