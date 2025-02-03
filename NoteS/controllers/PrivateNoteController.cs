using Microsoft.AspNetCore.Mvc;
using NoteS.tools.preconditions;
using NoteS.Attributes;
using NoteS.Models;
using NoteS.services;

namespace NoteS.controllers;

[ApiController]
[Route("api/{account_name}/notes")]
public class PrivateNoteController(AccountRegisterService registerService) : GeneralPreconditionController
{
    [HttpPut]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.EDIT_OWN_NOTES)]
    //TODO: что типа проверки, что ты можешь редактировать own ноты и ты own или ты просто вседозволено можешь менять все.
    [Route("{pathNote}")]
    [ProducesResponseType<Account>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    public IActionResult EditNote([FromRoute] string accountName,
        [FromRoute] string pathNote /*TODO: Пусть будет автогенерация GUID, потом юзер меняет*/,
        [FromBody] Object noteDto /*TODO: Дто с основными данными, типа блоков, которые надо добавить*/)
    {
        //TODO: DTO заметка ЮЗЕРА с контентом.
        return Execute(() => new Account(accountName, accountName), new EqualNameP(registerService, accountName));
    }

    [HttpPut]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.EDIT_OWN_NOTES)]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.EDIT_ALL_NOTES)]
    //TODO: что типа проверки, что ты можешь редактировать own ноты и ты own или ты просто вседозволено можешь менять все.
    [Route("private/{pathNote}")]
    [ProducesResponseType<Account>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    public IActionResult EditNoteAll([FromRoute] string accountName,
        [FromRoute] string pathNote /*TODO: Пусть будет автогенерация GUID, потом юзер меняет*/,
        [FromBody] Object noteDto /*TODO: Дто с основными данными, типа блоков, которые надо добавить*/)
    {
        //TODO: DTO заметка ЮЗЕРА с контентом.
        return Execute(() => new Account(accountName, accountName), new EqualNameP(registerService, accountName));
    }

    [HttpPut]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.EDIT_OWN_NOTES)]
    //TODO: что типа проверки, что ты можешь редактировать own ноты и ты own или ты просто вседозволено можешь менять все.
    [Route("content/{pathNote}")]
    [ProducesResponseType<Account>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    public IActionResult EditContentNote([FromRoute] string accountName,
        [FromRoute] string pathNote /*TODO: Пусть будет автогенерация GUID, потом юзер меняет*/,
        [FromBody] Object noteDto /*TODO: Дто с основными данными, типа блоков, которые надо добавить*/)
    {
        //TODO: DTO заметка ЮЗЕРА с контентом.
        return Execute(() => new Account(accountName, accountName), new EqualNameP(registerService, accountName));
    }

    [HttpPut]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.EDIT_OWN_NOTES)]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.EDIT_ALL_NOTES)]
    //TODO: что типа проверки, что ты можешь редактировать own ноты и ты own или ты просто вседозволено можешь менять все.
    [Route("content/private/{pathNote}")]
    [ProducesResponseType<Account>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    public IActionResult EditContentNoteAll([FromRoute] string accountName,
        [FromRoute] string pathNote /*TODO: Пусть будет автогенерация GUID, потом юзер меняет*/,
        [FromBody] Object noteDto /*TODO: Дто с основными данными, типа блоков, которые надо добавить*/)
    {
        //TODO: DTO заметка ЮЗЕРА с контентом.
        return Execute(() => new Account(accountName, accountName), new EqualNameP(registerService, accountName));
    }

    [HttpGet]
    [KeycloakAuthorize(Policies.SEARCH_OWN_NOTES)]
    [Route("search")]
    public IActionResult SearchNotes([FromRoute] string accountName,
            [FromQuery] String query) //TODO: Це выборка из эластика
        //TODO: List DTO заметок ЮЗЕРА без контента.
    {
        return Execute(() => new Account(accountName, accountName), new EqualNameP(registerService, accountName));
    }

    [HttpGet]
    [KeycloakAuthorize(Policies.SEARCH_OWN_NOTES)]
    [Route("semantic_search")]
    public IActionResult SemanticSearchNotes([FromRoute] string accountName,
            [FromQuery] String query) //TODO: Це выборка из эластика
        //TODO: List DTO заметок ЮЗЕРА без контента.
    {
        return Execute(() => new Account(accountName, accountName), new EqualNameP(registerService, accountName));
    }

    [HttpGet]
    [KeycloakAuthorize(Policies.READ_NOTES)]
    public IActionResult Notes([FromRoute] string accountName) //TODO: List DTO заметок ЮЗЕРА без контента.
    {
        return Execute(() => new Account(accountName, accountName), new EqualNameP(registerService, accountName));
    }

    [HttpGet]
    [KeycloakAuthorize(Policies.READ_NOTES)]
    [Route("{pathNote}")]
    public IActionResult GetNote([FromRoute] string accountName,
            [FromRoute] string pathNot)
        //TODO: DTO заметка ЮЗЕРА с контентом.
    {
        return Execute(() => new Account(accountName, accountName), new EqualNameP(registerService, accountName));
    }
}