using System.ComponentModel.DataAnnotations;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using LR.model;
using LR.model.dto.user;
using LR.services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.Tokens;
using Microsoft.OpenApi.Extensions;

namespace LR.controllers;

[Route("api/")]
[ApiController]
public class AccountController(
    AccountRegisterService registerService) : Controller
{
    //PUT /accounts/{name}
    [HttpPut]
    [Authorize]
    public AccountPartialDto Add([FromRoute] string accountName,
        [FromBody] AccountRegisterDto accountRegisterDto)
    {
        return registerService.Register(accountName, accountRegisterDto);
    }
}