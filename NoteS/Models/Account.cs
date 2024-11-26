using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Microsoft.VisualBasic;

namespace LR.model;

[Table("accounts")]
public class Account(Role role, string name)
{
    [Column("id")] public int? Id { get; init; }
    
    [Column("name")] [MaxLength(64)] public string Name { get; init; } = name;
    [Column("amount")] public Decimal Amount { get; set; }
    [Column("role")] public Role Role { get; set; } = role;
    
    [Column("password")] public string Password  { get; set; }
}