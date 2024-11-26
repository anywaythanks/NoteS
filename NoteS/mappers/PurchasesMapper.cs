using LR.model;
using LR.model.dto.purchases;

namespace LR.mappers;

public class PurchasesMapper(AccountMapper accountMapper, ProductMapper productMapper)
{
    public PurchasePartialDto ToPartialDto(Purchase purchase)
    {
        return new PurchasePartialDto(productMapper.ToPartialDto(purchase.Product),
            accountMapper.ToPartialDto(purchase.Buyer),
            purchase.Quantity, purchase.Cost);
    }
}