using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace NoteS.models.entity;

[Table("custom_type_syntax")]
public class CustomTypeSyntax(string name)
{
    [Column("id")] public int? Id { get; init; }

    [Column("name")] [MaxLength(64)] public string Name { get; init; } = name;
}