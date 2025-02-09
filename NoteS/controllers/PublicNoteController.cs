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
[Route("api/public/{accountName}/notes")]
public class PublicNoteController(
    AccountRegisterService registerService,
    NoteInformationService noteInformationService,
    NoteEditService editService,
    UniversalMapper um)
    : GeneralPreconditionController
{
    [HttpPatch]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.SET_OWN_PUBLIC_STATUS_NOTES)]
    [Route("{pathNote}/publish")]
    [ProducesResponseType<NoteEditPublicResponseDto>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [SwaggerOperation(Description = "Редактирование публичности заметки")]
    public Task<IActionResult> EditPublicNote([FromRoute] string accountName,
        [FromRoute] string pathNote,
        [FromBody] NoteEditPublicRequestDto editDto)
    {
        return Execute(() => um.OfEdit(editService.PublishNote(pathNote, accountName, editDto)),
            new EqualNameP(registerService, accountName));
    }

    [HttpPatch]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.EDIT_OWN_NOTES)]
    [Route("{pathNote}")]
    [ProducesResponseType<NoteEditContentResponseDto>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [SwaggerOperation(Description = "Редактирование контента заметки")]
    public Task<IActionResult> EditNote([FromRoute] string accountName,
        [FromRoute] string pathNote,
        [FromBody] NoteEditContentRequestDto editDto)
    {
        return Execute(async () => um.OfEditContent(await editService.EditContentNote(pathNote, accountName, editDto)),
            new EqualNameP(registerService, accountName));
    }

    [HttpPost]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.EDIT_OWN_NOTES)]
    [Route("{pathNote}/create")]
    [ProducesResponseType(StatusCodes.Status201Created)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [SwaggerOperation(Description = "Создание заметки")]
    public Task<IActionResult> CreateNote([FromRoute] string accountName,
        [FromRoute] string pathNote,
        [FromBody] NoteCreateRequestDto editDto)
    {
        return ExecuteA(async () =>
            {
                var r = um.OfCreateNote(await editService.CreateNote(pathNote, editDto));
                return Created(new Uri(Url.Link("GetNote", new { accountName, pathNote = r.Path }) ?? string.Empty),
                    "PublicNoteController");
            },
            new EqualNameP(registerService, accountName));
    }

    [HttpGet]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.SEARCH_OWN_NOTES)]
    [Route("search/title")]
    [ProducesResponseType<List<NoteSearchResponseDto>>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [SwaggerOperation(Description = "Поиск по заголовку")]
    public Task<IActionResult> SearchByTitleNotes([FromRoute] string accountName,
        [FromBody] NoteSearchRequestDto noteSearch)
    {
        return Execute(async () => um.OfSearch(await noteInformationService.Find(noteSearch.Title, accountName)),
            new EqualNameP(registerService, accountName));
    }

    [HttpGet]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.SEARCH_OWN_NOTES)]
    [Route("search/tag")]
    [ProducesResponseType<List<NoteSearchResponseDto>>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [SwaggerOperation(Description = "Поиск по тегу")]
    public Task<IActionResult> SearchByTagNotes([FromRoute] string accountName,
        [FromBody] NoteSearchTagsRequestDto noteSearch)
    {
        return Execute(async () => um.OfSearch(await noteInformationService.Find(noteSearch.Tag, accountName)),
            new EqualNameP(registerService, accountName));
    }

    [HttpDelete]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.DELETE_NOTES)]
    [ProducesResponseType<List<NoteSearchResponseDto>>(StatusCodes.Status204NoContent)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [Route("{pathNote}")]
    [SwaggerOperation(Description = "Удаление заметки")]
    public Task<IActionResult> DelNote([FromRoute] string accountName, [FromRoute] string pathNote)
    {
        return ExecuteA(async () => await editService.Delete(pathNote, accountName) ? NoContent() : throw new DontDel("Заметка"),
            new EqualNameP(registerService, accountName));
    }

    [HttpGet]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.SEARCH_OWN_NOTES)]
    [ProducesResponseType<List<NoteSearchResponseDto>>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [Route("search/semantic")]
    [SwaggerOperation(Description = "Семантический поиск")]
    public Task<IActionResult> SemanticSearchNotes([FromRoute] string accountName,
        [FromBody] NoteSemanticSearchRequestDto noteSearch)
    {
        return Execute(async () => um.OfSearch(
                await noteInformationService.FindSemantic(accountName, noteSearch.Query)),
            new EqualNameP(registerService, accountName));
    }

    [HttpGet]
    [KeycloakAuthorize(Policies.READ_NOTES)]
    [ProducesResponseType<List<NoteSearchResponseDto>>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [SwaggerOperation(Description = "Список заметок пользователя")]
    public Task<IActionResult> Notes([FromRoute] string accountName)
    {
        return Execute(() => um.OfSearch(noteInformationService.Find(accountName)),
            new EqualNameP(registerService, accountName));
    }

    [HttpGet]
    [KeycloakAuthorize(Policies.READ_NOTES)]
    [Route("{pathNote}")]
    [ProducesResponseType<NoteSearchContentResponseDto>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [SwaggerOperation(Description = "Данные заметки или комментария")]
    public Task<IActionResult> GetNote([FromRoute] string accountName,
        [FromRoute] string pathNote)
    {
        return Execute(() => um.OfContentSearch(noteInformationService.GetFull(pathNote, accountName)),
            new EqualNameP(registerService, accountName));
    }
}