using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using NoteS.models.dto.accounts;
using NoteS.services;
using NoteS.tools.preconditions;

namespace NoteS.controllers;

[Route("api/")]
[ApiController]
public class AccountController(EqualNameP equalNameP,
    AccountRegisterService registerService) : GeneralPreconditionController(equalNameP)
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