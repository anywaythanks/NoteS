using NoteS.exceptions;
using NoteS.models.entity;
using NoteS.repositories;

namespace NoteS.services;

public class AccountInformationService(
    IAccountRepository accountRepository)
{
    public Account Get(AccNameDto name)
    {
        return accountRepository.FindByName(name) ?? throw new NotFound("аккаунт");
    }

    public Account Get(AccIdDto id)
    {
        return accountRepository.FindById(id) ?? throw new NotFound("аккаунт");
    }
}