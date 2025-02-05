using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using NoteS.models.entity;

namespace NoteS.Models;

[Table("tags")]
public class Tag(string name)
{
    [Column("id")] public int? Id { get; init; }

    [Column("name")] [MaxLength(64)] public string Name { get; init; } = name;

    [Column("account_id")] [MaxLength(64)] public Account? Owner { get; set; }

    public List<NoteTag> Notes { get; init; } = []; //many-to-many
}