using Microsoft.AspNetCore.Mvc;
using NoteS.tools.preconditions;
using NoteS.Attributes;
using NoteS.Models;
using NoteS.models.dto;
using NoteS.models.mappers;
using NoteS.services;

namespace NoteS.controllers;

[ApiController]
[Route("api/{account_name}/notes")]
public class PrivateNoteController(
    AccountRegisterService registerService,
    NoteInformationService noteInformationService,
    UniversalMapper um)
    : GeneralPreconditionController
{
    [HttpPut]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.EDIT_OWN_NOTES)]
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
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.EDIT_ALL_NOTES)]
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
        return Execute(() => new Account(accountName, accountName), new EqualNameP(registerService, accountName));
    }

    [HttpGet]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.SEARCH_OWN_NOTES)]
    [Route("search/{title}")]
    [ProducesResponseType<List<NoteSearchResponseDto>>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    public IActionResult SearchByTitleNotes([FromRoute] string accountName,
        [FromBody] NoteSearchRequestDto noteSearch)
    {
        return Execute(() => um.OfSearch(noteInformationService.Find(noteSearch.Title, accountName)),
            new EqualNameP(registerService, accountName));
    }

    [HttpGet]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.SEARCH_OWN_NOTES)]
    [ProducesResponseType<List<NoteSearchResponseDto>>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [Route("semantic_search")]
    public IActionResult SemanticSearchNotes([FromRoute] string accountName,
            [FromBody] NoteSemanticSearchRequestDto noteSearch)
    {
        return Execute(() => um.OfSearch(
                noteInformationService.FindSemantic(accountName, noteSearch.Query)),
            new EqualNameP(registerService, accountName));
    }

    [HttpGet]
    [KeycloakAuthorize(Policies.READ_NOTES)]
    [ProducesResponseType<List<NoteSearchResponseDto>>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    public IActionResult Notes([FromRoute] string accountName)
    {
        return Execute(() => um.OfSearch(noteInformationService.Find(accountName)),
            new EqualNameP(registerService, accountName));
    }

    [HttpGet]
    [KeycloakAuthorize(Policies.READ_NOTES)]
    [Route("{pathNote}")]
    [ProducesResponseType<NoteSearchContentResponseDto>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    public IActionResult GetNote([FromRoute] string accountName,
        [FromRoute] string path)
    {
        return Execute(() => um.OfContentSearch(noteInformationService.GetFull(path, accountName)),
            new EqualNameP(registerService, accountName));
    }
}