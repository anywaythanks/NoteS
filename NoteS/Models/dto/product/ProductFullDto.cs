using LR.model.dto.user;

namespace LR.model.dto.product;

public class ProductFullDto(int? id, string name, string visibleName, string description, int quantity, Decimal cost, AccountFullDto owner)
{
    public int? Id { get; set; } = id;
    public AccountFullDto Owner { get; } = owner;
    public string Name { get; } = name;
    public string VisibleName { get; } = visibleName;
    public string Description { get; } = description;
    public int Quantity { get; set; } = quantity;
    public Decimal Cost { get; set; } = cost;
}