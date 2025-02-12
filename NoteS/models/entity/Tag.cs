using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace NoteS.models.entity;

[Table("tags")]
public partial class Tag(string name)
{
    [Column("id")] public int? Id { get; init; }

    [Column("name")] [MaxLength(64)] public string Name { get; init; } = name;

    [Column("account_id")] [MaxLength(64)] public int? Owner { get; set; }
}