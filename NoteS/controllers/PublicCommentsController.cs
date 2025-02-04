using Microsoft.AspNetCore.Mvc;
using NoteS.Attributes;
using NoteS.Models;
using NoteS.models.dto;
using NoteS.models.mappers;
using NoteS.services;
using NoteS.tools.preconditions;

namespace NoteS.controllers;

[ApiController]
[Route("api/public/{accountName}/notes/{pathNote}/comments")]
public class PublicCommentsController(
    AccountRegisterService registerService,
    CommentInformationService commentInformationService,
    CommentEditService commentEditService,
    UniversalMapper um)
    : GeneralPreconditionController
{
    [HttpGet]
    [KeycloakAuthorize(Policies.READ_COMMENTS)]
    [ProducesResponseType<List<NoteSearchContentResponseDto>>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    public IActionResult Comments([FromRoute] string accountName,
        [FromRoute] string pathNote)
    {
        return Execute(() => um.OfCommentsSearch(commentInformationService.Comments(pathNote, accountName)),
            new EqualNameP(registerService, accountName));
    }

    [HttpPost]
    [KeycloakAuthorize(Policies.READ_COMMENTS, Policies.READ_NOTES, Policies.CREATE_COMMENTS)]
    [ProducesResponseType<CommentCreateResponseDto>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    public IActionResult CreateComment([FromRoute] string accountName,
        [FromRoute] string pathNote,
        [FromBody] CommentCreateRequestDto createDto)
    {
        return Execute(() => um.OfCreateComment(commentEditService.CreateComment(accountName, pathNote, createDto)),
            new EqualNameP(registerService, accountName));
    }

    [HttpPost]
    [KeycloakAuthorize(Policies.READ_COMMENTS, Policies.READ_NOTES, Policies.EDIT_OWN_COMMENTS)]
    [Route("{pathComment}")]
    [ProducesResponseType<CommentEditResponseDto>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    public IActionResult EditComment([FromRoute] string accountName,
        [FromRoute] string pathNote,
        [FromRoute] string pathComment,
        [FromBody] CommentEditRequestDto createDto)
    {
        return Execute(() => um.OfEditComment(commentEditService.EditContentComment(accountName, pathNote, createDto)),
            new EqualNameP(registerService, accountName));
    }
}