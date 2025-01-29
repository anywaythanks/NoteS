using LR.model;
using LR.model.dto.product;
using NoteS.Models;
using Riok.Mapperly.Abstractions;

namespace LR.mappers;

[Mapper(RequiredMappingStrategy = RequiredMappingStrategy.Target,
    ThrowOnMappingNullMismatch = true)]
public partial class ProductMapper(AccountMapper accountMapper)
{
    [UseMapper] private AccountMapper AccountMapper => accountMapper;

    public partial ProductPartialDto ToPartialDto(Product product);

    public partial ProductFullDto ToFullDto(Product product);

    public partial Product ToModel(ProductFullDto fullDto);

    public partial Product ToModel(ProductPartialDto partialDto);
}