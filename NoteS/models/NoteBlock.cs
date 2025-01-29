using System.ComponentModel.DataAnnotations.Schema;
using NoteS.models;

namespace NoteS.Models;

[Table("note_blocks")]
public class NoteBlock
{
    [Column("id")] public int? Id { get; init; }
    [Column("order")] public required int Order { get; init; }
    [Column("note_id")] public required Note OwnerNote { get; init; }
    [Column("content")] public required string Content { get; init; }
    [Column("syntax_types_id")] public required SyntaxType Type { get; init; }
}