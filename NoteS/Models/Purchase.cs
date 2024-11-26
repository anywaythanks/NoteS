using System.ComponentModel;
using System.ComponentModel.DataAnnotations.Schema;

namespace LR.model;

[Table("purchases")]
public class Purchase(int quantity, Decimal cost)
{
    [Column("id")] public int? Id { get; init; }
    

    [Column("number")]
    public int? Number { get; init; }

    [Column("product_id")] public Product Product { get; set; } //FK


    [Column("account_id")] public Account Buyer { get; set; } //FK
    [Column("quantity")] public int Quantity { get; init; } = quantity;
    [Column("cost")] public Decimal Cost { get; init; } = cost;
}