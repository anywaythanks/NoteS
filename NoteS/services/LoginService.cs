using System.IdentityModel.Tokens.Jwt;
using LR.model;
using LR.model.dto.user;
using LR.repositories;
using Microsoft.AspNetCore.Identity;
using NoteS.Models;
using static Microsoft.AspNetCore.Identity.PasswordVerificationResult;

namespace LR.services;

public class LoginService(IAccountRepository accountRepository, PasswordHasher<Account> passwordHasher)
{
    public Account? Login(string accountName, AccountLoginDto loginDto)
    {
        // // находим пользователя 
        // var account = accountRepository.FindByName(accountName);
        // if (account is null) return null;
        // return passwordHasher.VerifyHashedPassword(account, account.Password, loginDto.Password) == Success
        //     ? account
        //     : null;
        return null!;
    }
}