using NoteS.exceptions;
using NoteS.models.dto;
using NoteS.models.entity;
using NoteS.repositories;

namespace NoteS.services;

public class AccountRegisterService(IAccountRepository accountRepository)
{
    public void Register(AccNameDto accountName, AccUuidDto uuid)
    {
        Account? account = accountRepository.FindByName(accountName) ??
                           accountRepository.FindByUuid(uuid);
        if (account != null) return;
        account = new Account(accountName.Name, uuid.Uuid);
        if (account.Name != accountName.Name || account.Uuid != uuid.Uuid) throw new Forbidden("аккаунту");
        accountRepository.Save(account);
    }
}