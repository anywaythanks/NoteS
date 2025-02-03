using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace NoteS.Models;

[Table("accounts")]
public class Account(string name, string uid)
{
    [Column("id")] public int? Id { get; init; }

    [Column("name")] [MaxLength(128)] public string Name { get; init; } = name;

    [Column("uid")] [MaxLength(128)] public string Uid { get; init; } = uid;
}