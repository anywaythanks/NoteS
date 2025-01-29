using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace NoteS.Models;

[Table("products")]
public class Product(string name, string visibleName, string description)
{
    [Column("id")] public int? Id { get; init; }
    [Column("account_id")] public Account Owner { get; set; }

    [Column("name")]
    [Required]
    [MaxLength(64)]
    public string Name { get; init; } = name; //UK

    [Column("visible_name")]
    [Required]
    [MaxLength(128)]
    public string VisibleName { get; set; } = visibleName;

    [Column("description")] public string Description { get; set; } = description;
    [Column("quantity")] public int Quantity { get; set; }
    [Column("cost")] public Decimal Cost { get; set; }
}