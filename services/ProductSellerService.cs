using System.ComponentModel.DataAnnotations;
using LR.exceptions;
using LR.mappers;
using LR.model;
using LR.model.dto.product;
using LR.model.dto.purchases;
using LR.repositories;

namespace LR.services;

public class ProductSellerService(
    ProductRepositoryDb productRepository,
    IAccountRepository accountRepository,
    AccountReplenishService accountReplenishService,
    ProductInformationService productInformationService,
    AccountMapper accountMapper,
    PurchasesRegisterService purchasesRegisterService,
    ProductMapper productMapper)
{
    public PurchasePartialDto Buy(string accountName, string productName, int quantity)
    {
        using var transactionProduct = productRepository.Database.BeginTransaction();

        try
        {
            var product = productRepository.FindByName(productName) ?? throw new NotFound();
            var cost = quantity * product.Cost;
            if (product.Quantity < quantity) throw new NotEnoughProducts();
            product.Quantity -= quantity;
            var acc = product.Owner.Name == accountName
                ? product.Owner
                : accountRepository.FindByName(accountName) ?? throw new NotFound();
            accountReplenishService.Credit(acc, cost);
            productRepository.Save(product);
            transactionProduct.Commit();
            return purchasesRegisterService.Register(new Purchase(
                quantity,
                cost) { Product = product, Buyer = acc });
        }
        catch (Exception e)
        {
            transactionProduct.Rollback();
            throw;
        }
    }

    public ProductPartialDto Replenish(string accountName, string productName,
        [Required] [Range(1, long.MaxValue, ErrorMessage = "Only positive quantity allowed")]
        int quantity)
    {
        var p = productRepository.FindByName(productName) ?? throw new NotFound();
        if (p.Owner.Name != accountName) throw new Forbidden();
        p.Owner = accountRepository.Detach(p.Owner);
        p.Quantity += quantity;
        productRepository.Save(p);
        return productInformationService.GetPartial(productName);
    }

    private ProductPartialDto Put(ProductFullDto product,
        [Required] [Range(1, long.MaxValue, ErrorMessage = "Only positive quantity allowed")]
        int quantity)
    {
        product.Quantity += quantity;
        var p = productRepository.Detach(productRepository.Save(productMapper.ToModel(product)));
        return productMapper.ToPartialDto(p);
    }
}