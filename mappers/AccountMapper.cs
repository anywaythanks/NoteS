using LR.model;
using LR.model.dto.user;

namespace LR.mappers;

public class AccountMapper
{
    public AccountFullDto ToFullDto(Account account)
    {
        return new AccountFullDto(account.Id, account.Role, account.Name, account.Amount, account.Password);
    }

    public AccountPartialDto ToPartialDto(Account account)
    {
        return new AccountPartialDto(account.Role, account.Name, account.Amount);
    }

    public Account ToModel(AccountPartialDto partialDto)
    {
        return new Account(partialDto.Role, partialDto.Name) { Amount = partialDto.Amount };
    }

    public Account ToModel(AccountFullDto fullDto)
    {
        return new Account(fullDto.Role, fullDto.Name)
            { Id = fullDto.Id, Amount = fullDto.Amount, Password = fullDto.Password };
    }
}