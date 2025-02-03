using Microsoft.AspNetCore.Identity;
using NoteS.Models;
using NoteS.models.dto.accounts;
using NoteS.repositories;
using AccountMapper = NoteS.mappers.AccountMapper;


namespace NoteS.services;

public class AccountRegisterService()
{
    public AccountPartialDto Register(string accountName, AccountRegisterDto registerDto)
    {
        // Account account = accountRepository.FindByName(accountName) ??
        //                   new Account(registerDto.Role, accountName);
        // account.Password = passwordHasher.HashPassword(account, registerDto.Password);
        // account.Role = registerDto.Role;
        // return accountMapper.ToPartialDto(accountRepository.Detach(accountRepository.Save(account)));
        return null!;
    }
}