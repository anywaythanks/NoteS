using LR.exceptions;
using LR.model.dto.user;
using LR.repositories;
using AccountMapper = NoteS.mappers.AccountMapper;

namespace LR.services;

public class AccountInformationService(IAccountRepository accountRepository, AccountMapper accountMapper)
{
    public AccountPartialDto GetPartial(string accountName)
    {
        var a = accountRepository.FindByName(accountName) ?? throw new NotFound();
        return accountMapper.ToPartialDto(accountRepository.Detach(a));
    }

    public AccountFullDto Get(string accountName)
    {
        var a = accountRepository.FindByName(accountName) ?? throw new NotFound();
        return accountMapper.ToFullDto(accountRepository.Detach(a));
    }
}