using Microsoft.AspNetCore.Mvc;
using NoteS.Attributes;
using NoteS.exceptions;
using NoteS.Models;
using NoteS.models.dto;
using NoteS.models.mappers;
using NoteS.services;
using NoteS.tools.preconditions;
using Swashbuckle.AspNetCore.Annotations;

namespace NoteS.controllers;

[ApiController]
[Route("api/public/{accountName}/tags")]
public class PublicTagsController(
    TagInformationService tagInformationService,
    AccountRegisterService registerService,
    TagEditService tagEditService,
    UniversalMapper um)
    : GeneralPreconditionController
{
    [HttpGet]
    [KeycloakAuthorize(Policies.READ_NOTES)]
    [ProducesResponseType<List<TagResponseDto>>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [Route("{pathNote}")]
    [SwaggerOperation(Description = "Теги конкрентной заметки")]
    public Task<IActionResult> Tags([FromRoute] string accountName,
        [FromRoute] string pathNote)
    {
        return Execute(() => um.Of(tagInformationService.Tags(pathNote, accountName)),
            new EqualNameP(registerService, accountName));
    }

    [HttpPost]
    [KeycloakAuthorize(Policies.CREATE_NOTES)]
    [ProducesResponseType<TagResponseDto>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [SwaggerOperation(Description = "Создание тега")]
    public Task<IActionResult> CreateTag([FromRoute] string accountName,
        [FromBody] CreateTagRequestDto createTagRequestDto)
    {
        return Execute(() => um.Of(tagEditService.Create(accountName, createTagRequestDto.Name)),
            new EqualNameP(registerService, accountName));
    }

    [HttpGet]
    [ProducesResponseType<List<TagResponseDto>>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [KeycloakAuthorize(Policies.READ_NOTES)]
    [SwaggerOperation(Description = "Список тегов пользователя")]
    public Task<IActionResult> Tags([FromRoute] string accountName)
    {
        return Execute(() => um.Of(tagInformationService.Tags(accountName)),
            new EqualNameP(registerService, accountName));
    }

    [HttpDelete]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.DELETE_NOTES)]
    [ProducesResponseType(StatusCodes.Status204NoContent)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [Route("{pathNote}")]
    [SwaggerOperation(Description = "Удаление тега для заметки")]
    public Task<IActionResult> DelTag([FromRoute] string accountName,
        [FromRoute] string pathNote,
        [FromBody] DeleteTagRequestDto delete)
    {
        return ExecuteA(() => Task.FromResult<IActionResult>(
                tagEditService.Delete(pathNote, accountName, delete.Name) ? NoContent() : throw new DontDel("Тег")),
            new EqualNameP(registerService, accountName));
    }

    [HttpPost]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.CREATE_NOTES)]
    [Route("{pathNote}")]
    [SwaggerOperation(Description = "Добавить тег к заметке")]
    public Task<IActionResult> AddTag([FromRoute] string accountName,
        [FromRoute] string pathNote,
        [FromBody] CreateTagRequestDto createTagRequestDto)
    {
        return Execute(() => um.Of(tagEditService.Add(pathNote, accountName, createTagRequestDto.Name)),
            new EqualNameP(registerService, accountName));
    }
}