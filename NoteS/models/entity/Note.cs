using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using NoteS.models;

namespace NoteS.Models;

[Table("notes")]
public class Note(string title, string elasticUuid)
{
    [Column("id")] public int? Id { get; set; }

    [Column("path")] [MaxLength(128)] public string? Path { get; set; } //TODO: дефолт значение = юид
    [Column("title")] [MaxLength(128)] public string Title { get; set; } = title;
    [Column("elastic_uuid")] [MaxLength(128)] public string ElasticUuid { get; set; } = elasticUuid;
    public string? Content { get; set; }//TODO: из эластика
    [Column("account_id")] public required Account Owner { get; set; }
    public required List<Tag> Tags { get; init; } //many-to-many
    
    [Column("type")] public NoteTypes? Type { get; set; }
    [Column("is_public")] public bool IsPublic { get; set; }
    [Column("prev")] public Note? MainNote { get; set; } //поскольку это дерево, то не следует вовсе заполнять
    [Column("syntax_type_id")] public SyntaxType? SyntaxType { get; set; }
}