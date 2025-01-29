using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using LR.model;

namespace NoteS.Models;

[Table("notes")]
public class Note(Role role, string name, string title)
{
    [Column("id")] public int? Id { get; init; }

    [Column("name")] [MaxLength(64)] public string Name { get; init; } = name;
    [Column("title")] [MaxLength(64)] public string Title { get; init; } = title;
    [Column("account_id")] public required Account Owner { get; set; }
    public required List<Tag> Tags { get; init; }//many-to-many
}