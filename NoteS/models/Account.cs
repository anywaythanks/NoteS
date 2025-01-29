using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using LR.model;

namespace NoteS.Models;

[Table("accounts")]
public class Account(Role role, string name, string uid)
{
    [Column("id")] public int? Id { get; init; }

    [Column("name")] [MaxLength(128)] public string Name { get; init; } = name;

    [Column("uid")] [MaxLength(128)] public string Uid { get; init; } = uid;


    /// <summary>
    /// LEGACY
    /// </summary>
    [Column("amount")]
    public Decimal Amount { get; set; }

    [Column("password")] public string Password { get; set; }
    [Column("role")] public Role Role { get; set; } = role;
}