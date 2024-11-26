using LR.exceptions;
using LR.mappers;
using LR.model;
using LR.model.dto.product;
using LR.model.dto.user;
using LR.repositories;

namespace LR.services;

public class ProductInformationService(IProductRepository productRepository, ProductMapper productMapper)
{
    public ProductPartialDto GetPartial(string name)
    {
        var p = productRepository.FindByName(name) ?? throw new NotFound();
        return productMapper.ToPartialDto(productRepository.Detach(p));
    }

    public List<ProductPartialDto> GetPartials()
    {
        var l = productRepository.Products();
        return l.Select(productMapper.ToPartialDto).ToList();
    }

    public ProductFullDto Get(string name)
    {
        var p = productRepository.FindByName(name) ?? throw new NotFound();
        return productMapper.ToFullDto(productRepository.Detach(p));
    }
}