﻿using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration.UserSecrets;
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
[Route("api/public/{accountName}/notes/{pathNote}/comments")]
public class PublicCommentsController(
    AccountRegisterService registerService,
    CommentInformationService commentInformationService,
    CommentEditService commentEditService,
    UniversalDtoMapper um,
    AccountMapper am,
    NoteMapper nm)
    : GeneralPreconditionController
{
    [HttpGet]
    [KeycloakAuthorize(Policies.READ_COMMENTS)]
    [ProducesResponseType<List<NoteSearchContentResponseDto>>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [SwaggerOperation(Description = "Список комментариев к заметке")]
    public Task<IActionResult> Comments([FromRoute] AccountName accountName,
        [FromRoute] NotePath pathNote)
    {
        return Execute(() => um.OfCommentsSearch(commentInformationService.Comments(
                am.Of(accountName), nm.Of(pathNote))),
            new EqualNameP(registerService, am.Of(accountName)));
    }

    [HttpPost]
    [KeycloakAuthorize(Policies.READ_COMMENTS, Policies.READ_NOTES, Policies.CREATE_COMMENTS)]
    [ProducesResponseType(StatusCodes.Status201Created)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [SwaggerOperation(Description = "Создание комментария к заметке")]
    public Task<IActionResult> CreateComment([FromRoute] AccountName accountName,
        [FromRoute] NotePath pathNote,
        [FromBody] CommentCreateRequestDto createDto)
    {
        return ExecuteA( async () =>
            {
                var r = um.OfCreateComment(
                    await commentEditService.CreateComment(am.Of(accountName), nm.Of(pathNote), createDto));
                return Created(new Uri(Url.Link("GetNote",
                    new { accountName, pathNote = r.Path }) ?? string.Empty), "PublicNoteController");
            },
            new EqualNameP(registerService, am.Of(accountName)));
    }

    [HttpPost]
    [KeycloakAuthorize(Policies.READ_COMMENTS, Policies.READ_NOTES, Policies.EDIT_OWN_COMMENTS)]
    [Route("{pathComment}")]
    [ProducesResponseType<CommentEditResponseDto>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [SwaggerOperation(Description = "Редактирование комментария")]
    public Task<IActionResult> EditComment([FromRoute] AccountName accountName,
        [FromRoute] NotePath pathNote,
        [FromRoute] NotePath pathComment,
        [FromBody] CommentEditRequestDto createDto)
    {
        return Execute(
            async () => um.OfEditComment(await commentEditService.EditContentComment(nm.Of(pathNote), am.Of(accountName), createDto)),
            new EqualNameP(registerService, am.Of(accountName)));
    }

    [HttpDelete]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.DELETE_COMMENTS)]
    [ProducesResponseType<List<NoteSearchResponseDto>>(StatusCodes.Status204NoContent)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [Route("{pathComment}")]
    [SwaggerOperation(Description = "Удаление комментария")]
    public Task<IActionResult> DelComment([FromRoute] AccountName accountName, [FromRoute] NotePath pathComment)
    {
        return ExecuteA(async () => await commentEditService.Delete(nm.Of(pathComment), am.Of(accountName))
                ? NoContent()
                : throw new DontDel("Комментарий"),
            new EqualNameP(registerService, am.Of(accountName)));
    }
}