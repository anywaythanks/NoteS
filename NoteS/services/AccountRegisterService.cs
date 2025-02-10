using NoteS.exceptions;
using NoteS.models.entity;
using NoteS.repositories;

namespace NoteS.services;

public class AccountRegisterService(IAccountRepository accountRepository)
{
    public Account Register(Field<IAccName, string> accountName, Field<IAccUuid, string> uuid)
    {
        Account account = accountRepository.FindByName(accountName) ??
                          accountRepository.FindByUuid(uuid) ??
                          new Account(accountName.Val, uuid.Val);
        if (account.Name != accountName.Val || account.Uuid != uuid.Val) throw new Forbidden("аккаунту");
        return accountRepository.Save(account);
    }
}