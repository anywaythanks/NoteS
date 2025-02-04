using NoteS.exceptions;
using NoteS.Models;
using NoteS.repositories;

namespace NoteS.services;

public class AccountRegisterService(IAccountRepository accountRepository)
{
    public Account Register(string accountName, string uuid)
    {
        Account account = accountRepository.FindByName(accountName) ??
                          accountRepository.FindByUuid(uuid) ??
                          new Account(accountName, uuid);
        if (account.Name != accountName || account.Uid != uuid) throw new Forbidden("аккаунту");
        return accountRepository.Save(account);
    }
}