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
    AccountRegisterService registerService,
    AccountInformationService accountInformationService,
    AccountReplenishService accountReplenishService,
    LoginService loginService) : Controller
{
    //PUT /accounts/{name}
    [HttpPut]
    [AllowAnonymous]
    [Route("[controller]/{accountName}")]
    public AccountPartialDto Add([FromRoute] string accountName,
        [FromBody] AccountRegisterDto accountRegisterDto)
    {
        return registerService.Register(accountName, accountRegisterDto);
    }

    [HttpPost]
    [Authorize]
    [Route("[controller]/{accountName}/replenish/")]
    public IResult Replenish([FromRoute] string accountName,
        [Required] [Range(0, 100000)] Decimal value)
    {
        return accountName != User.Identity?.Name
            ? Results.Unauthorized()
            : Results.Json(accountReplenishService.Debit(accountName, value));
    }

    [HttpPost]
    [AllowAnonymous]
    [Route("/login/{accountName}")]
    public IResult Login([FromRoute] string accountName,
        [FromBody] AccountLoginDto loginDto)
    {
        var account = loginService.Login(accountName, loginDto);
        if (account is null) return Results.Unauthorized(); //401

        var claims = new List<Claim>
            { new(ClaimTypes.Name, accountName), new(ClaimTypes.Role, account.Role.GetDisplayName()) };

        var jwt = new JwtSecurityToken(
            issuer: AuthOptions.ISSUER,
            audience: AuthOptions.AUDIENCE,
            claims: claims,
            expires: DateTime.UtcNow.Add(TimeSpan.FromMinutes(30)),
            signingCredentials: new SigningCredentials(AuthOptions.GetSymmetricSecurityKey(),
                SecurityAlgorithms.HmacSha256));
        var encodedJwt = new JwtSecurityTokenHandler().WriteToken(jwt);
        return Results.Json(new
        {
            token = encodedJwt,
            username = accountName
        });
    }
   
   
    [HttpGet]
    [Authorize]
    [Route("[controller]/{accountName}")]
    public IResult Get([FromRoute] string accountName)
    {
        return accountName != User.Identity?.Name
            ? Results.Unauthorized()
            : Results.Json(accountInformationService.GetPartial(accountName));
    }
}