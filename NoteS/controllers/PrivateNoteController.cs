using System.ComponentModel;
using Microsoft.AspNetCore.Mvc;
using NoteS.tools.preconditions;
using NoteS.Attributes;
using NoteS.Models;
using NoteS.models.dto;
using NoteS.models.mappers;
using NoteS.services;
using Swashbuckle.AspNetCore.Annotations;

namespace NoteS.controllers;

[ApiController]
[Route("api/private/{accountName}/notes")]
public class PrivateNoteController(
    AccountRegisterService registerService,
    NoteInformationService noteInformationService,
    NoteEditService editService,
    UniversalMapper um)
    : GeneralPreconditionController
{
    [HttpPatch]
    [KeycloakAuthorize(Policies.READ_ALL_NOTES)]
    [KeycloakAuthorize(Policies.SET_ALL_PUBLIC_STATUS_NOTES)]
    [Route("{pathNote}/publish")]
    [ProducesResponseType<Account>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [SwaggerOperation(Description = "Редактирование свойства публичности любой заметки или комментария")]
    public IActionResult EditPublicAllNote([FromRoute] string accountName,
        [FromRoute] string pathNote,
        [FromBody] NoteEditPublicRequestDto editDto)
    {
        return Execute(() => um.OfEdit(editService.PublishNote(pathNote, editDto)),
            new EqualNameP(registerService, accountName));
    }

    [HttpPatch]
    [KeycloakAuthorize(Policies.READ_ALL_NOTES, Policies.EDIT_ALL_NOTES)]
    [Route("{pathNote}")]
    [ProducesResponseType<NoteEditContentResponseDto>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [SwaggerOperation(Description = "Редактирование контента любой заметки или комментария")]
    public IActionResult EditNoteAll([FromRoute] string accountName,
        [FromRoute] string pathNote,
        [FromBody] NoteEditContentRequestDto editDto)
    {
        return Execute(() => um.OfEditContent(editService.EditContentNote(pathNote, editDto)),
            new EqualNameP(registerService, accountName));
    }

    [HttpGet]
    [KeycloakAuthorize(Policies.READ_ALL_NOTES)]
    [Route("{pathNote}")]
    [ProducesResponseType<NoteCreateResponseDto>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [SwaggerOperation(Description = "Получение данных любой заметки или комментария")]
    public IActionResult GetNoteAll([FromRoute] string accountName)
    {
        return Execute(() => um.OfCreateNote(noteInformationService.GetFull(accountName)),
            new EqualNameP(registerService, accountName));
    }
}