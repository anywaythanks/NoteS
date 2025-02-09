﻿using NoteS.exceptions;
using NoteS.models.entity;
using NoteS.repositories;

namespace NoteS.services;

public class AccountInformationService(
    IAccountRepository accountRepository)
{
    public Account Get(Field<IAccName, string> name)
    {
        return accountRepository.FindByName(name) ?? throw new NotFound("аккаунт");
    }
}