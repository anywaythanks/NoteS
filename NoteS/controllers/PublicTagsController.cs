using Microsoft.AspNetCore.Mvc;
using NoteS.Attributes;
using NoteS.exceptions;
using NoteS.models.dto;
using NoteS.models.entity;
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
    UniversalDtoMapper um,
    AccountMapper am,
    NoteMapper nm,
    TagMapper tm)
    : GeneralPreconditionController
{
    [HttpGet]
    [KeycloakAuthorize(Policies.READ_NOTES)]
    [ProducesResponseType<List<TagResponseDto>>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [Route("{pathNote}")]
    [SwaggerOperation(Description = "Теги конкрентной заметки")]
    public Task<IActionResult> Tags([FromRoute] AccountName accountName,
        [FromRoute] NotePath pathNote)
    {
        return Execute(() => um.Of(tagInformationService.Tags(nm.Of(pathNote), am.Of(accountName))),
            new EqualNameP(registerService, am.Of(accountName)));
    }

    [HttpPost]
    [KeycloakAuthorize(Policies.CREATE_NOTES)]
    [ProducesResponseType<TagResponseDto>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [SwaggerOperation(Description = "Создание тега")]
    public Task<IActionResult> CreateTag([FromRoute] AccountName accountName,
        [FromBody] CreateTagRequestDto createTagRequestDto)
    {
        return Execute(() => um.Of(tagEditService.Create(am.Of(accountName), tm.Of(createTagRequestDto))),
            new EqualNameP(registerService, am.Of(accountName)));
    }

    [HttpGet]
    [ProducesResponseType<List<TagResponseDto>>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [KeycloakAuthorize(Policies.READ_NOTES)]
    [SwaggerOperation(Description = "Список тегов пользователя")]
    public Task<IActionResult> Tags([FromRoute] AccountName accountName)
    {
        return Execute(() => um.Of(tagInformationService.Tags(am.Of(accountName))),
            new EqualNameP(registerService, am.Of(accountName)));
    }

    [HttpDelete]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.DELETE_NOTES)]
    [ProducesResponseType(StatusCodes.Status204NoContent)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [Route("{pathNote}")]
    [SwaggerOperation(Description = "Удаление тега для заметки")]
    public Task<IActionResult> DelTag([FromRoute] AccountName accountName,
        [FromRoute] NotePath pathNote,
        [FromBody] DeleteTagRequestDto delete)
    {
        return ExecuteA(
            () => Task.FromResult<IActionResult>(tagEditService.Delete(nm.Of(pathNote), am.Of(accountName),
                tm.Of(delete))
                ? NoContent()
                : throw new DontDel("Тег")),
            new EqualNameP(registerService, am.Of(accountName)));
    }

    [HttpPost]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.CREATE_NOTES)]
    [Route("{pathNote}")]
    [SwaggerOperation(Description = "Добавить тег к заметке")]
    public Task<IActionResult> AddTag([FromRoute] AccountName accountName,
        [FromRoute] NotePath pathNote,
        [FromBody] CreateTagRequestDto createTagRequestDto)
    {
        return Execute(() => um.Of(tagEditService.Add(nm.Of(pathNote), am.Of(accountName),
                tm.Of(createTagRequestDto))),
            new EqualNameP(registerService, am.Of(accountName)));
    }
}