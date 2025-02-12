using System.Text.Json.Serialization;
using NoteS.models.dto;
using NoteS.models.mappers;

namespace NoteS.models.entity;

using static MappersInstances;

public partial class Note
{
    public static implicit operator NoteIdDto(Note note) => nm.ToIdDto(note);
    public static implicit operator AccIdDto(Note note) => nm.ToAccIdDto(note);
    public static implicit operator NotePathDto(Note note) => nm.ToPathDto(note);
    public static implicit operator NoteTitleDto(Note note) => nm.ToTitleDto(note);
    public static implicit operator NoteElasticDto(Note note) => nm.ToElasticDto(note);
    public static implicit operator NoteContentDto(Note note) => nm.ToContentDto(note);
    public static implicit operator NoteContentDto?(Note? note) => note == null ? null : nm.ToContentDto(note);
    public static implicit operator NoteEditPublicResponseDto(Note note) => um.OfEdit(note);
    public static implicit operator NoteEditContentResponseDto(Note note) => um.OfEditContent(note);
    public static implicit operator NoteCreateResponseDto(Note note) => um.OfCreateNote(note);
    public static implicit operator NoteIsPublicDto(Note note) => nm.ToIsPublicDto(note);
    public static implicit operator CommentCreateResponseDto(Note note) => um.OfCreateComment(note);
    public static implicit operator CommentEditResponseDto(Note note) => um.OfEditComment(note);
    public static implicit operator NoteCreateRequestDto(Note note) => um.OfCreateContent(note);
}

public readonly struct NoteIdDto(int id)
{
    public int Id { get; init; } = id;
}

public readonly struct NotePathDto(string path)
{
    public string Path { get; init; } = path;
}

public readonly struct NoteTitleDto(string title)
{
    public string Title { get; init; } = title;
}

public readonly struct NoteElasticDto(string elasticUuid)
{
    public string ElasticUuid { get; init; } = elasticUuid;
}

public readonly struct SearchResultDto(string elasticUuid)
{
    public string ElasticUuid { get; init; } = elasticUuid;
    public decimal Score { get; init; }
    public string Content { get; init; }
}

public readonly struct NoteContentDto(string content)
{
    [JsonPropertyName("content")] public string Content { get; init; } = content;
}

public class ElasticResponseDto
{
    public string _id { get; set; }
    public string Id { get; set; }
    public string Title { get; set; }
    public int Owner { get; set; }
    public string Content { get; set; }

    public static implicit operator NoteContentDto?(ElasticResponseDto? note) =>
        note == null ? null : nm.ToContentDto(note);

    public static implicit operator NoteContentDto(ElasticResponseDto note) => nm.ToContentDto(note);
}

public readonly struct NoteIsPublicDto(bool isPublic)
{
    public bool IsPublic { get; init; } = isPublic;
}