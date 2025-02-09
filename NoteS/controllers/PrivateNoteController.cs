using Microsoft.AspNetCore.Mvc;
using NoteS.Attributes;
using NoteS.models.dto;
using NoteS.models.entity;
using NoteS.models.mappers;
using NoteS.services;
using NoteS.tools.preconditions;
using Swashbuckle.AspNetCore.Annotations;

namespace NoteS.controllers;

[ApiController]
[Route("api/private/{accountName}/notes")]
public class PrivateNoteController(
    AccountRegisterService registerService,
    NoteInformationService noteInformationService,
    NoteEditService editService,
    UniversalDtoMapper um,
    AccountMapper am,
    NoteMapper nm)
    : GeneralPreconditionController
{
    [HttpPatch]
    [KeycloakAuthorize(Policies.READ_ALL_NOTES, Policies.SET_ALL_PUBLIC_STATUS_NOTES)]
    [Route("{pathNote}/publish")]
    [ProducesResponseType<Account>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [SwaggerOperation(Description = "Редактирование свойства публичности любой заметки или комментария")]
    public Task<IActionResult> EditPublicAllNote([FromRoute] AccountName accountName,
        [FromRoute] NotePath pathNote,
        [FromBody] NoteEditPublicRequestDto editDto)
    {
        return Execute(() => um.OfEdit(editService.PublishNote(nm.Of(pathNote), editDto)),
            new EqualNameP(registerService, am.Of(accountName)));
    }

    [HttpPatch]
    [KeycloakAuthorize(Policies.READ_ALL_NOTES, Policies.EDIT_ALL_NOTES)]
    [Route("{pathNote}")]
    [ProducesResponseType<NoteEditContentResponseDto>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [SwaggerOperation(Description = "Редактирование контента любой заметки или комментария")]
    public Task<IActionResult> EditNoteAll([FromRoute] AccountName accountName,
        [FromRoute] NotePath pathNote,
        [FromBody] NoteEditContentRequestDto editDto)
    {
        return Execute(async () => um.OfEditContent(await editService.EditContentNote(nm.Of(pathNote), editDto)),
            new EqualNameP(registerService, am.Of(accountName)));
    }

    [HttpGet]
    [KeycloakAuthorize(Policies.READ_ALL_NOTES)]
    [Route("{pathNote}")]
    [ProducesResponseType<NoteCreateResponseDto>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [SwaggerOperation(Description = "Получение данных любой заметки или комментария")]
    public Task<IActionResult> GetNoteAll([FromRoute] AccountName accountName,
        [FromRoute] NotePath pathNote)
    {
        return Execute(() => um.OfCreateNote(noteInformationService.GetFull(nm.Of(pathNote))),
            new EqualNameP(registerService, am.Of(accountName)));
    }
}