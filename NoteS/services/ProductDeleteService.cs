using LR.exceptions;
using LR.mappers;
using LR.model;
using LR.model.dto.product;
using LR.repositories;
using NoteS.Models;

namespace LR.services;

public class ProductDeleteService(
    IProductRepository productRepository,
    ProductMapper productMapper)
{
    public ProductPartialDto Delete(string accountName, string productName)
    {
         Product product = productRepository.FindByName(productName) ?? throw new NotFound();
        return productMapper.ToPartialDto(productRepository.Delete(product));
    }
}