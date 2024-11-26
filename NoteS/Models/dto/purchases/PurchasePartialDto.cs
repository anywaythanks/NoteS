using LR.model.dto.product;
using LR.model.dto.user;

namespace LR.model.dto.purchases;

public class PurchasePartialDto(ProductPartialDto product, AccountPartialDto buyer, int quantity, decimal cost)
{
 
    public ProductPartialDto Product { get; } = product;
    public AccountPartialDto Buyer { get; } = buyer;
    public int Quantity { get; } = quantity;
    public Decimal Cost { get; } = cost;
}