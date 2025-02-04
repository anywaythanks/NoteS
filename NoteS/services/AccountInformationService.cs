using NoteS.exceptions;
using NoteS.Models;
using NoteS.repositories;

namespace NoteS.services;

public class AccountInformationService(
    IAccountRepository accountRepository)
{
    public Account Get(string accountName)
    {
        return accountRepository.FindByName(accountName) ?? throw new NotFound("аккаунт");
    }
}