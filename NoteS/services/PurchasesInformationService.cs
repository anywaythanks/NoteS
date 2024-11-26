using LR.mappers;
using LR.model.dto.purchases;
using LR.repositories;

namespace LR.services;

public class PurchasesInformationService(IPurchasesRepository purchasesRepository, PurchasesMapper purchasesMapper)
{
    public List<PurchasePartialDto> GetAll()
    {
        return purchasesRepository.FindAll()
            .Select(purchasesRepository.Detach)
            .Select(purchasesMapper.ToPartialDto)
            .ToList();
    }
}