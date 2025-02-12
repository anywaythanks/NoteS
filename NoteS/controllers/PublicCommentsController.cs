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
public class PublicCommentsController(
    AccountRegisterService register,
    CommentInformationService commentInformationService,
    CommentEditService commentEditService,
    UniversalDtoMapper um) : GeneralPreconditionController(register)
{
    [HttpGet("notes/{pathNote}/comments")]
    [KeycloakAuthorize(Policies.READ_COMMENTS)]
    [SwaggerOperation(Description = "Список комментариев к заметке")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    public PageDto<NoteSearchContentResponseDto> Comments([FromQuery] AccName accountName,
        [FromQuery] NotePath pathNote,
        [FromQuery] PaginationRequestDto pagination)
    {
        Check(accountName);
        var comments = commentInformationService.Comments(accountName, pathNote, pagination, pagination);
        return um.OfPage(comments);
    }

    [HttpPost("notes/{pathNote}/comments")]
    [KeycloakAuthorize(Policies.READ_COMMENTS, Policies.READ_NOTES, Policies.CREATE_COMMENTS)]
    [SwaggerOperation(Description = "Создание комментария к заметке")]
    [ProducesResponseType(StatusCodes.Status201Created)]
    public async Task<ActionResult<CommentCreateResponseDto>> CreateComment([FromQuery] AccName accountName,
        [FromQuery] NotePath pathNote,
        [FromBody] CommentCreateRequestDto createDto)
    {
        Check(accountName);
        CommentCreateResponseDto note = await commentEditService.CreateComment(accountName, pathNote, createDto);

        return Created(Url.Action("GetNote", "PublicNote",
            new { accountName.AccountName, pathNote = note.Path }, Request.Scheme), note);
    }

    [HttpPost]
    [KeycloakAuthorize(Policies.READ_COMMENTS, Policies.READ_NOTES, Policies.EDIT_OWN_COMMENTS)]
    [Route("comments/{pathNote}")]
    [SwaggerOperation(Description = "Редактирование комментария")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    public async Task<CommentEditResponseDto> EditComment([FromQuery] AccName accountName,
        [FromQuery] NotePath pathNote,
        [FromBody] CommentEditRequestDto createDto)
    {
        Check(accountName);

        return await commentEditService.EditContentComment(pathNote, accountName, createDto);
    }

    [HttpDelete]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.DELETE_COMMENTS)]
    [Route("comments/{pathNote}")]
    [SwaggerOperation(Description = "Удаление комментария")]
    [ProducesResponseType(StatusCodes.Status204NoContent)]
    public async Task<ActionResult> DelComment([FromQuery] AccName accountName,
        [FromQuery] NotePath pathNote)
    {
        Check(accountName);

        await commentEditService.Delete(pathNote, accountName);
        return NoContent();
    }
}