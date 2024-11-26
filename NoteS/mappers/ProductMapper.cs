using LR.model;
using LR.model.dto.product;

namespace LR.mappers;

public class ProductMapper(AccountMapper accountMapper)
{
    public ProductPartialDto ToPartialDto(Product product)
    {
        return new ProductPartialDto(product.Name, product.VisibleName, product.Description, product.Quantity,
            product.Cost, accountMapper.ToPartialDto(product.Owner));
    }

    public ProductFullDto ToFullDto(Product product)
    {
        return new ProductFullDto(product.Id, product.Name, product.VisibleName, product.Description, product.Quantity,
            product.Cost, accountMapper.ToFullDto(product.Owner));
    }

    public Product ToModel(ProductFullDto fullDto)
    {
        return new Product(fullDto.Name, fullDto.VisibleName,
            fullDto.Description)
        {
            Id = fullDto.Id, Quantity = fullDto.Quantity, Cost = fullDto.Cost,
            Owner = accountMapper.ToModel(fullDto.Owner)
        };
    }

    public Product ToModel(ProductPartialDto partialDto)
    {
        return new Product(partialDto.Name, partialDto.VisibleName, partialDto.Description)
            { Quantity = partialDto.Quantity, Cost = partialDto.Cost, Owner = accountMapper.ToModel(partialDto.Owner) };
    }
}