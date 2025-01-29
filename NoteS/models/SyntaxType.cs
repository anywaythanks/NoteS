using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace NoteS.models;

public class SyntaxType(string name)
{
    [Column("id")] public int? Id { get; init; }
    [Column("name")] [MaxLength(64)] public string Name { get; init; } = name;
}