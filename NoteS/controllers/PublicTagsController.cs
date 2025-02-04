using Microsoft.AspNetCore.Mvc;
using NoteS.Attributes;
using NoteS.Models;
using NoteS.models.dto;
using NoteS.models.mappers;
using NoteS.services;
using NoteS.tools.preconditions;

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
    public IActionResult Tags([FromRoute] string accountName,
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
    public IActionResult CreateTag([FromRoute] string accountName,
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
    public IActionResult Tags([FromRoute] string accountName)
    {
        return Execute(() => um.Of(tagInformationService.Tags(accountName)),
            new EqualNameP(registerService, accountName));
    }

    [HttpDelete]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.DELETE_NOTES)]
    [ProducesResponseType(StatusCodes.Status204NoContent)] //TODO: сделай
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [Route("{pathNote}")]
    public IActionResult DelTag([FromRoute] string accountName,
        [FromRoute] string pathNote,
        [FromBody] DeleteTagRequestDto delete)
    {
        return Execute(() => tagEditService.Delete(pathNote, accountName, delete.Name),
            new EqualNameP(registerService, accountName));
    }

    [HttpPost]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.CREATE_NOTES)]
    [Route("{pathNote}")]
    public IActionResult AddTag([FromRoute] string accountName,
        [FromRoute] string pathNote,
        [FromBody] CreateTagRequestDto createTagRequestDto)
    {
        return Execute(() => um.Of(tagEditService.Add(pathNote, accountName, createTagRequestDto.Name)),
            new EqualNameP(registerService, accountName));
    }
}