using Microsoft.AspNetCore.Mvc;
using NoteS.Attributes;
using NoteS.exceptions;
using NoteS.models.dto;
using NoteS.models.entity;
using NoteS.models.mappers;
using NoteS.services;
using NoteS.tools;
using Swashbuckle.AspNetCore.Annotations;

namespace NoteS.controllers;

[ApiController]
[Route("api/public/{accountName}")]
[ProducesResponseType(StatusCodes.Status403Forbidden)]
[ProducesResponseType(StatusCodes.Status401Unauthorized)]
public class PublicTagsController(
    TagInformationService tagInformationService,
    AccountRegisterService register,
    TagEditService tagEditService,
    UniversalDtoMapper um)
    : GeneralPreconditionController(register)
{
    [HttpGet("notes/{pathNote}/tags")]
    [KeycloakAuthorize(Policies.READ_NOTES)]
    [SwaggerOperation(Description = "Теги конкретной заметки")]
    public List<TagResponseDto> Tags([FromQuery] AccName accountName,
        [FromQuery] NotePath pathNote)
    {
        Check(accountName);
        var tags = tagInformationService.Tags(pathNote, accountName);
        return um.Of(tags);
    }

    [HttpPost("tags")]
    [KeycloakAuthorize(Policies.CREATE_NOTES)]
    [SwaggerOperation(Description = "Создание тега")]
    public CreatedResult CreateTag([FromQuery] AccName accountName,
        [FromBody] CreateTagRequestDto createTagRequestDto)
    {
        Check(accountName);
        TagResponseDto note = tagEditService.Create(accountName, createTagRequestDto);
        return Created(Url.Action("Tags", "PublicTags",
            new { accountName.AccountName }, Request.Scheme), note);
    }

    [HttpGet("tags")]
    [KeycloakAuthorize(Policies.READ_NOTES)]
    [SwaggerOperation(Description = "Список тегов пользователя")]
    public List<TagResponseDto> Tags([FromQuery] AccName accountName)
    {
        Check(accountName);

        return um.Of(tagInformationService.Tags(accountName));
    }

    [HttpDelete("notes/{pathNote}/tags/{tagName}")]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.DELETE_NOTES)]
    [SwaggerOperation(Description = "Удаление тега для заметки")]
    public NoContentResult DelTag([FromQuery] AccName accountName,
        [FromQuery] NotePath pathNote,
        [FromQuery] TagNameRequestDto delete)
    {
        Check(accountName);

        tagEditService.Delete(pathNote, accountName, delete);
        return NoContent();
    }

    [HttpPost("notes/{pathNote}/tags")]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.CREATE_NOTES)]
    [SwaggerOperation(Description = "Добавить тег к заметке")]
    public ActionResult AddTag([FromQuery] AccName accountName,
        [FromQuery] NotePath pathNote,
        [FromBody] AddTagRequestDto createTagRequestDto)
    {
        Check(accountName);
        var isCreate = tagEditService.Add(pathNote, accountName, createTagRequestDto);
        return isCreate ? Created() : Ok();
    }
}