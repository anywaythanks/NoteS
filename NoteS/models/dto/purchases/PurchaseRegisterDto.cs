using LR.model.dto.product;
using LR.model.dto.user;

namespace LR.model.dto.purchases;

public class PurchaseRegisterDto(ProductFullDto product, AccountFullDto buyer, int quantity, decimal cost)
{
    public ProductFullDto Product { get; } = product;
    public AccountFullDto Buyer { get; } = buyer;
    public int Quantity { get; } = quantity;
    public Decimal Cost { get; } = cost;
}