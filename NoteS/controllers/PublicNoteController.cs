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
[Route("api/public/{accountName}/notes")]
public class PublicNoteController(
    AccountRegisterService registerService,
    NoteInformationService noteInformationService,
    TagInformationService tagInformationService,
    NoteEditService editService,
    UniversalDtoMapper um,
    AccountMapper am,
    NoteMapper nm,
    TagMapper tm)
    : GeneralPreconditionController
{
    [HttpPatch]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.SET_OWN_PUBLIC_STATUS_NOTES)]
    [Route("{pathNote}/publish")]
    [ProducesResponseType<NoteEditPublicResponseDto>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [SwaggerOperation(Description = "Редактирование публичности заметки")]
    public Task<IActionResult> EditPublicNote([FromQuery] AccName accountName,
        [FromQuery] NotePath pathNote,
        [FromBody] NoteEditPublicRequestDto editDto)
    {
        return Execute(() => um.OfEdit(editService.PublishNote(nm.Of(pathNote), am.Of(accountName), editDto)),
            new EqualNameP(registerService, am.Of(accountName)));
    }

    [HttpPatch]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.EDIT_OWN_NOTES)]
    [Route("{pathNote}")]
    [ProducesResponseType<NoteEditContentResponseDto>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [SwaggerOperation(Description = "Редактирование контента заметки")]
    public Task<IActionResult> EditNote([FromQuery] AccName accountName,
        [FromQuery] NotePath pathNote,
        [FromBody] NoteEditContentRequestDto editDto)
    {
        return Execute(
            async () => um.OfEditContent(
                await editService.EditContentNote(nm.Of(pathNote), am.Of(accountName), editDto)),
            new EqualNameP(registerService, am.Of(accountName)));
    }

    [HttpPost]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.EDIT_OWN_NOTES)]
    [Route("{pathNote}/create")]
    [ProducesResponseType(StatusCodes.Status201Created)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [SwaggerOperation(Description = "Создание заметки")]
    public Task<IActionResult> CreateNote([FromQuery] AccName accountName,
        [FromQuery] NotePath pathNote,
        [FromBody] NoteCreateRequestDto editDto)
    {
        return ExecuteA(async () =>
            {
                var r = um.OfCreateNote(await editService.CreateNote(am.Of(accountName), editDto));
                return Created(Url.Action("GetNote", "PublicNote",
                    new { accountName.AccountName, pathNote = r.Path }, Request.Scheme), r);
            },
            new EqualNameP(registerService, am.Of(accountName)));
    }

    [HttpGet]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.SEARCH_OWN_NOTES)]
    [Route("search/title")]
    [ProducesResponseType<List<NoteSearchResponseDto>>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [SwaggerOperation(Description = "Поиск по заголовку")]
    public Task<IActionResult> SearchByTitleNotes([FromQuery] AccName accountName,
        [FromBody] NoteSearchRequestDto noteSearch)
    {
        return Execute(
            async () => um.OfSearch(await noteInformationService.Find(nm.Of(noteSearch), am.Of(accountName))),
            new EqualNameP(registerService, am.Of(accountName)));
    }

    [HttpGet]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.SEARCH_OWN_NOTES)]
    [Route("search/tag")]
    [ProducesResponseType<List<NoteSearchResponseDto>>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [SwaggerOperation(Description = "Поиск по тегу")]
    public Task<IActionResult> SearchByTagNotes([FromQuery] AccName accountName,
        [FromBody] NoteSearchTagsRequestDto noteSearch)
    {
        return Execute(() => um.OfSearch(tagInformationService.FindTag(tm.Of(noteSearch), am.Of(accountName))),
            new EqualNameP(registerService, am.Of(accountName)));
    }

    [HttpDelete]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.DELETE_NOTES)]
    [ProducesResponseType<List<NoteSearchResponseDto>>(StatusCodes.Status204NoContent)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [Route("{pathNote}")]
    [SwaggerOperation(Description = "Удаление заметки")]
    public Task<IActionResult> DelNote([FromQuery] AccName accountName, [FromQuery] NotePath pathNote)
    {
        return ExecuteA(
            async () => await editService.Delete(nm.Of(pathNote),
                am.Of(accountName))
                ? NoContent()
                : throw new DontDel("Заметка"),
            new EqualNameP(registerService, am.Of(accountName)));
    }

    [HttpGet]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.SEARCH_OWN_NOTES)]
    [ProducesResponseType<List<NoteSearchResponseDto>>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [Route("search/semantic")]
    [SwaggerOperation(Description = "Семантический поиск")]
    public Task<IActionResult> SemanticSearchNotes([FromQuery] AccName accountName,
        [FromBody] NoteSemanticSearchRequestDto noteSearch)
    {
        return Execute(async () => um.OfSearch(
                await noteInformationService.FindSemantic(am.Of(accountName), um.Of(noteSearch))),
            new EqualNameP(registerService, am.Of(accountName)));
    }

    [HttpGet]
    [KeycloakAuthorize(Policies.READ_NOTES)]
    [ProducesResponseType<List<NoteSearchResponseDto>>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [SwaggerOperation(Description = "Список заметок пользователя")]
    public Task<IActionResult> Notes([FromQuery] AccName accountName)
    {
        return Execute(() => um.OfSearch(noteInformationService.Find(am.Of(accountName))),
            new EqualNameP(registerService, am.Of(accountName)));
    }

    [HttpGet]
    [KeycloakAuthorize(Policies.READ_NOTES)]
    [Route("{pathNote}")]
    [ProducesResponseType<NoteSearchContentResponseDto>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [SwaggerOperation(Description = "Данные заметки или комментария")]
    public Task<IActionResult> GetNote([FromQuery] AccName accountName,
        [FromQuery] NotePath pathNote)
    {
        return Execute(() => um.OfContentSearch(noteInformationService.GetFull(nm.Of(pathNote), am.Of(accountName))),
            new EqualNameP(registerService, am.Of(accountName)));
    }
}