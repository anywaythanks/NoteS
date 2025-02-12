using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;
using NoteS.models.entity;

namespace NoteS.models.dto;

public class NoteEditPublicRequestDto
{
    [JsonPropertyName("is_public")] public bool IsPublic { get; set; }
}

public class NoteEditPublicResponseDto
{
    public string Title { get; set; }
    [JsonPropertyName("is_public")] public bool IsPublic { get; set; }
}

public class NoteEditContentRequestDto
{
    public string Title { get; set; }
    public string Content { get; set; }
    [JsonIgnore] public SyntaxType Type { get; set; }

    [JsonPropertyName("syntax_name")]
    [Required]
    [EnumDataType(typeof(SyntaxType.SyntaxTypeName))]
    public SyntaxType.SyntaxTypeName TypeName
    {
        get => Type.Name;
        set => Type = SyntaxType.NameToType(value);
    }
}

public class NoteEditContentResponseDto
{
    public string Title { get; set; }
    public string Content { get; set; }
    [JsonIgnore] public SyntaxType SyntaxType { get; set; }

    [JsonPropertyName("syntax_name")]
    [Required]
    [EnumDataType(typeof(SyntaxType.SyntaxTypeName))]
    public SyntaxType.SyntaxTypeName TypeName
    {
        get => SyntaxType.Name;
        set => SyntaxType = SyntaxType.NameToType(value);
    }
}

public class NoteCreateRequestDto
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

public class NoteCreateResponseDto
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
}

public class SemanticSearchQuery(string query)
{
    public string Query { get; init; } = query;
}