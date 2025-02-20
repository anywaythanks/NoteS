using Microsoft.AspNetCore.Mvc;
using NoteS.Attributes;
using NoteS.models.dto;
using NoteS.models.entity;
using NoteS.services;
using NoteS.tools;
using Swashbuckle.AspNetCore.Annotations;

namespace NoteS.controllers;

[ApiController]
[Route("api/private/{accountName}/notes")]
[ProducesResponseType(StatusCodes.Status200OK)]
[ProducesResponseType(StatusCodes.Status403Forbidden)]
[ProducesResponseType(StatusCodes.Status401Unauthorized)]
public class PrivateNoteController(
    AccountRegisterService register,
    NoteInformationService noteInformationService,
    NoteEditService editService)
    : GeneralPreconditionController(register)
{
    [HttpPatch("{pathNote}/publish")]
    [KeycloakAuthorize(Policies.READ_ALL_NOTES, Policies.SET_ALL_PUBLIC_STATUS_NOTES)]
    [SwaggerOperation(Description = "Редактирование свойства публичности любой заметки или комментария")]
    public NoteEditPublicResponseDto EditPublicAllNote([FromQuery] AccName accountName,
        [FromQuery] NotePath pathNote,
        [FromBody] NoteEditPublicRequestDto editDto)
    {
        Check(accountName);

        return editService.PublishNote(pathNote, editDto);
    }

    [HttpPatch("{pathNote}/content")]
    [KeycloakAuthorize(Policies.READ_ALL_NOTES, Policies.EDIT_ALL_NOTES)]
    [SwaggerOperation(Description = "Редактирование любой заметки или комментария")]
    public NoteEditOtherResponseDto EditNoteAll([FromQuery] AccName accountName,
        [FromQuery] NotePath pathNote,
        [FromBody] NoteEditOtherRequestDto editDto)
    {
        Check(accountName);

        return editService.EditNote(pathNote, editDto);
    }

    [HttpPost("{pathNote}/content")]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.EDIT_OWN_NOTES)]
    [SwaggerOperation(Description = "Редактирование контента заметки (комментария в другом месте)")]
    public async Task<NoteEditContentResponseDto> EditContentNote([FromQuery] AccName accountName,
        [FromQuery] NotePath pathNote,
        [FromBody] NoteEditOnlyContentRequestDto editDto)
    {
        Check(accountName);

        return await editService.EditNote(pathNote, accountName, editDto);
    }

    [HttpGet("{pathNote}")]
    [KeycloakAuthorize(Policies.READ_ALL_NOTES)]
    [SwaggerOperation(Description = "Получение данных любой заметки или комментария")]
    public async Task<NoteCreateResponseDto> GetNoteAll([FromQuery] AccName accountName,
        [FromQuery] NotePath pathNote)
    {
        Check(accountName);

        return await noteInformationService.GetFull(pathNote);
    }
}