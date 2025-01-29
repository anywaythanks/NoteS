using LR.exceptions;
using LR.mappers;
using LR.model;
using LR.model.dto.product;
using LR.repositories;
using NoteS.Models;

namespace LR.services;

public class ProductRegisterService(
    IProductRepository productRepository,
    ProductMapper productMapper,
    AccountMapper accountMapper,
    AccountInformationService accountInformationService)
{
    public ProductPartialDto Register(string accountName, string productName, ProductRegisterDto registerDto)
    {
        var a = accountMapper.ToModel(accountInformationService.Get(accountName));
        Product product = productRepository.FindByName(productName) ??
                          new Product(productName, registerDto.VisibleName, registerDto.Description)
                              { Cost = registerDto.Cost, Owner = a };
        if (product.Owner.Name != a.Name) throw new Forbidden();
        product.VisibleName = registerDto.VisibleName;
        product.Cost = registerDto.Cost;
        product.Description = registerDto.Description;
        return productMapper.ToPartialDto(productRepository.Save(product));
    }
}