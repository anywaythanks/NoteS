using Microsoft.AspNetCore.Mvc;
using NoteS.models.mappers;
using NoteS.services;
using NoteS.tools.preconditions;

namespace NoteS.controllers;

[Route("api/public/{accountName}")]
[ApiController]
public class AccountController(
    AccountRegisterService registerService,
    UniversalMapper um) : GeneralPreconditionController
{
    // //PUT /accounts/{name}
    // [HttpPut]
    // [Authorize]
    // public IActionResult Add([FromRoute] string accountName,
    //     [FromBody] AccountRegisterRequestDto accountRegisterDto)
    // {
    //     return Execute(() => um.Of(registerService.Register(accountName, accountRegisterDto)));
    // }
}