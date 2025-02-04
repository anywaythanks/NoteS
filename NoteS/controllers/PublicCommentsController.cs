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
    [SwaggerOperation(Description = "Список комментариев к заметке")]
    public IActionResult Comments([FromRoute] string accountName,
        [FromRoute] string pathNote)
    {
        return Execute(() => um.OfCommentsSearch(commentInformationService.Comments(pathNote, accountName)),
            new EqualNameP(registerService, accountName));
    }

    [HttpPost]
    [KeycloakAuthorize(Policies.READ_COMMENTS, Policies.READ_NOTES, Policies.CREATE_COMMENTS)]
    [ProducesResponseType(StatusCodes.Status201Created)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [SwaggerOperation(Description = "Создание комментария к заметке")]
    public IActionResult CreateComment([FromRoute] string accountName,
        [FromRoute] string pathNote,
        [FromBody] CommentCreateRequestDto createDto)
    {
        return ExecuteA(() => {
                var r = um.OfCreateComment(
                    commentEditService.CreateComment(accountName, pathNote, createDto));
                return Created(new Uri(Url.Link("GetNote", new { accountName, pathNote = r.Path }) ?? string.Empty),
                    "PublicNoteController");
            },
            new EqualNameP(registerService, accountName));
    }

    [HttpPost]
    [KeycloakAuthorize(Policies.READ_COMMENTS, Policies.READ_NOTES, Policies.EDIT_OWN_COMMENTS)]
    [Route("{pathComment}")]
    [ProducesResponseType<CommentEditResponseDto>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [SwaggerOperation(Description = "Редактирование комментария")]
    public IActionResult EditComment([FromRoute] string accountName,
        [FromRoute] string pathNote,
        [FromRoute] string pathComment,
        [FromBody] CommentEditRequestDto createDto)
    {
        return Execute(() => um.OfEditComment(commentEditService.EditContentComment(accountName, pathNote, createDto)),
            new EqualNameP(registerService, accountName));
    }
    
    [HttpDelete]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.DELETE_COMMENTS)]
    [ProducesResponseType<List<NoteSearchResponseDto>>(StatusCodes.Status204NoContent)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [Route("{pathComment}")]
    [SwaggerOperation(Description = "Удаление комментария")]
    public IActionResult DelComment([FromRoute] string accountName, [FromRoute] string pathComment)
    {
        return ExecuteA(() => commentEditService.Delete(pathComment, accountName) ? NoContent() : throw new DontDel("Комментарий"),
            new EqualNameP(registerService, accountName));
    }
}