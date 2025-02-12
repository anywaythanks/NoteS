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
[Route("api/public/{accountName}/notes")]
[ProducesResponseType(StatusCodes.Status403Forbidden)]
[ProducesResponseType(StatusCodes.Status401Unauthorized)]
public class PublicNoteController(
    AccountRegisterService register,
    NoteInformationService noteInformationService,
    TagInformationService tagInformationService,
    NoteEditService editService,
    UniversalDtoMapper um,
    TagMapper tm)
    : GeneralPreconditionController(register)
{
    [HttpPatch("{pathNote}/publish")]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.SET_OWN_PUBLIC_STATUS_NOTES)]
    [SwaggerOperation(Description = "Редактирование публичности заметки/комменатрия")]
    public NoteEditPublicResponseDto EditPublicNote([FromQuery] AccName accountName,
        [FromQuery] NotePath pathNote,
        [FromBody] NoteEditPublicRequestDto editDto)
    {
        Check(accountName);

        return editService.PublishNote(pathNote, accountName, editDto);
    }

    [HttpPost("{pathNote}")]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.EDIT_OWN_NOTES)]
    [SwaggerOperation(Description = "Редактирование заметки (комментария в другом месте)")]
    public async Task<NoteEditContentResponseDto> EditNote([FromQuery] AccName accountName,
        [FromQuery] NotePath pathNote,
        [FromBody] NoteEditContentRequestDto editDto)
    {
        Check(accountName);

        return await editService.EditNote(pathNote, accountName, editDto);
    }

    [HttpPost]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.EDIT_OWN_NOTES)]
    [SwaggerOperation(Description = "Создание заметки")]
    public async Task<CreatedResult> CreateNote([FromQuery] AccName accountName,
        [FromBody] NoteCreateRequestDto editDto)
    {
        Check(accountName);
        NoteCreateResponseDto r = await editService.CreateNote(accountName, editDto);
        return Created(Url.Action("GetNote", "PublicNote",
            new { accountName.AccountName, pathNote = r.Path }, Request.Scheme), r);
    }

    [HttpGet("search/title")]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.SEARCH_OWN_NOTES)]
    [SwaggerOperation(Description = "Поиск по заголовку")]
    public async Task<PageDto<NoteSearchContentResponseDto>> SearchByTitleNotes([FromQuery] AccName accountName,
        [FromQuery] NoteSearchRequestDto noteSearch,
        [FromQuery] PaginationRequestDto pagination)
    {
        Check(accountName);
        var notes = await noteInformationService.Find(noteSearch, accountName, pagination, pagination);
        return um.OfContent(notes);
    }

    [HttpGet("search/tag")]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.SEARCH_OWN_NOTES)]
    [SwaggerOperation(Description = "Поиск по тегам")]
    public PageDto<NoteSearchContentResponseDto> SearchByTagNotes([FromQuery] AccName accountName,
        [FromQuery(Name = "tag")] List<string> tags,
        [FromQuery(Name = "filter")] List<string> filterTags,
        [FromQuery] PaginationRequestDto pagination,
        [FromQuery(Name = "and")] bool isAnd = true
    )
    {
        Check(accountName);
        var notes = tagInformationService.FindTags(
            tm.Of(tags), tm.Of(filterTags),
            accountName, isAnd, pagination, pagination);
        return um.OfContent(notes);
    }

    [HttpDelete("{pathNote}")]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.DELETE_NOTES)]
    [SwaggerOperation(Description = "Удаление заметки")]
    public async Task<NoContentResult> DelNote([FromQuery] AccName accountName, [FromQuery] NotePath pathNote)
    {
        Check(accountName);
        await editService.Delete(pathNote, accountName);
        return NoContent();
    }

    [HttpGet("search/semantic")]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.SEARCH_OWN_NOTES)]
    [SwaggerOperation(Description = "Семантический поиск")]
    public async Task<PageDto<NoteSearchContentResponseDto>> SemanticSearchNotes([FromQuery] AccName accountName,
        [FromQuery] NoteSemanticSearchRequestDto noteSearch,
        [FromQuery] PaginationRequestDto pagination)
    {
        Check(accountName);
        var notes = await noteInformationService.FindSemantic(accountName, noteSearch, pagination, pagination);
        return um.OfContent(notes);
    }

    [HttpGet]
    [KeycloakAuthorize(Policies.READ_NOTES)]
    [SwaggerOperation(Description = "Список заметок пользователя")]
    public PageDto<NoteSearchContentResponseDto> Notes([FromQuery] AccName accountName,
        [FromQuery] PaginationRequestDto pagination)
    {
        Check(accountName);
        var notes = noteInformationService.Find(accountName, pagination, pagination);
        return um.OfContent(notes);
    }

    [HttpGet("{pathNote}")]
    [KeycloakAuthorize(Policies.READ_NOTES)]
    [SwaggerOperation(Description = "Данные заметки или комментария")]
    public async Task<NoteSearchContentResponseDto> GetNote([FromQuery] AccName accountName,
        [FromQuery] NotePath pathNote)
    {
        Check(accountName);
        var notes = await noteInformationService.GetFullPublic(pathNote, accountName);
        return um.OfContentSearch(notes);
    }
}