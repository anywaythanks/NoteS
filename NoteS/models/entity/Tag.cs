using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace NoteS.Models;

[Table("tags")]
public class Tag(string name, Account owner)
{
    [Column("id")] public int? Id { get; init; }

    [Column("name")] [MaxLength(64)] public string Name { get; init; } = name;

    [Column("account_id")] [MaxLength(64)] public Account Owner { get; init; } = owner;
}