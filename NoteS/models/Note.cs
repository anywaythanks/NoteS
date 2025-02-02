using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using NoteS.models;

namespace NoteS.Models;

[Table("notes")]
public class Note(string title, string elasticUuid)
{
    [Column("id")] public int? Id { get; init; }

    [Column("path")] [MaxLength(64)] public string? Path { get; init; } //TODO: дефолт значение = юид
    [Column("title")] [MaxLength(128)] public string Title { get; init; } = title;
    [Column("title")] [MaxLength(128)] public string ElasticUuid { get; init; } = elasticUuid;
    [Column("account_id")] public required Account Owner { get; set; }
    public required List<Tag> Tags { get; init; } //many-to-many
    [Column("type")] public NoteTypes? Type { get; init; }
    [Column("is_public")] public bool? Public { get; init; }
    [Column("prev")] public Note? MainNote { get; init; } //поскольку это дерево, то не следует вовсе заполнять
    [Column("syntax_type_id")] public SyntaxType? SyntaxType { get; init; }
}