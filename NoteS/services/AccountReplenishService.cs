using System.ComponentModel.DataAnnotations;
using LR.exceptions;
using LR.mappers;
using LR.model;
using LR.model.dto.user;
using LR.repositories;

namespace LR.services;

public class AccountReplenishService(
    IAccountRepository accountRepository,
    AccountInformationService accountInformationService,
    AccountMapper accountMapper)
{
    public AccountPartialDto Debit(string accountName,
        [Required] [Range(1, long.MaxValue, ErrorMessage = "Only positive number allowed")]
        Decimal amount)
    {
        var aDto = accountInformationService.Get(accountName);
        var a = accountMapper.ToModel(aDto);
        a.Amount += amount;
        return accountMapper.ToPartialDto(accountRepository.Detach(accountRepository.Save(a)));
    }

    public AccountPartialDto Credit(Account account,
        [Required] [Range(1, long.MaxValue, ErrorMessage = "Only positive number allowed")]
        Decimal amount)
    {
        account.Amount -= amount;
        if (account.Amount < 0) throw new NotEnoughAmount();
        return accountMapper.ToPartialDto(accountRepository.Detach(accountRepository.Save(account)));
    }
}