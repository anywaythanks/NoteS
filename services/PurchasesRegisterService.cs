using LR.exceptions;
using LR.mappers;
using LR.model;
using LR.model.dto.product;
using LR.model.dto.purchases;
using LR.model.dto.user;
using LR.repositories;

namespace LR.services;

public class PurchasesRegisterService(
    IPurchasesRepository purchasesRepository,
    PurchasesMapper purchasesMapper,
    ProductMapper productMapper,
    AccountMapper accountMapper)
{
    public List<PurchasePartialDto> GetAll()
    {
        return purchasesRepository.FindAll().Select(purchasesMapper.ToPartialDto).ToList();
    }

    public PurchasePartialDto Register(Purchase purchase)
    {
        return purchasesMapper.ToPartialDto(purchasesRepository.Detach(purchasesRepository.Save(purchase)));
    }
}