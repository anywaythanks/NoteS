using LR.model;
using LR.model.dto.purchases;
using Riok.Mapperly.Abstractions;

namespace LR.mappers;

[Mapper(RequiredMappingStrategy = RequiredMappingStrategy.Target,
    ThrowOnMappingNullMismatch = true)]
public partial class PurchasesMapper(AccountMapper accountMapper, ProductMapper productMapper)
{
    [UseMapper] private AccountMapper AccountMapper => accountMapper;
    [UseMapper] private ProductMapper ProductMapper => productMapper;
    
    public partial PurchasePartialDto ToPartialDto(Purchase purchase);
}