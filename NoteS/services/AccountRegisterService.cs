using LR.mappers;
using LR.model;
using LR.model.dto.user;
using LR.repositories;
using Microsoft.AspNetCore.Identity;
using NoteS.Models;


namespace LR.services;

public class AccountRegisterService(
    IAccountRepository accountRepository,
    AccountMapper accountMapper,
    PasswordHasher<Account> passwordHasher)
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