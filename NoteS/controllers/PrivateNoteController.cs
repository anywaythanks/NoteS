using Microsoft.AspNetCore.Mvc;
using NoteS.tools.preconditions;
using NoteS.Attributes;
using NoteS.Models;
using NoteS.models.dto;
using NoteS.models.mappers;
using NoteS.services;

namespace NoteS.controllers;

[ApiController]
[Route("api/{accountName}/notes")]
public class PrivateNoteController(
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
    public IActionResult EditPublicNote([FromRoute] string accountName,
        [FromRoute] string pathNote,
        [FromBody] NoteEditPublicRequestDto editDto)
    {
        return Execute(() => um.OfEdit(editService.PublishNote(pathNote, accountName, editDto)),
            new EqualNameP(registerService, accountName));
    }

    [HttpPatch]
    [KeycloakAuthorize(Policies.READ_All_NOTES, Policies.SET_ALL_PUBLIC_STATUS_NOTES)]
    [Route("private/{pathNote}/publish")]
    [ProducesResponseType<Account>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    public IActionResult EditPublicAllNote([FromRoute] string accountName,
        [FromRoute] string pathNote,
        [FromBody] NoteEditPublicRequestDto editDto)
    {
        return Execute(() => um.OfEdit(editService.PublishNote(pathNote, editDto)),
            new EqualNameP(registerService, accountName));
    }

    [HttpPatch]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.EDIT_OWN_NOTES)]
    [Route("{pathNote}")]
    [ProducesResponseType<NoteEditContentResponseDto>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    public IActionResult EditNote([FromRoute] string accountName,
        [FromRoute] string pathNote,
        [FromBody] NoteEditContentRequestDto editDto)
    {
        return Execute(() => um.OfEditContent(editService.EditContentNote(pathNote, accountName, editDto)),
            new EqualNameP(registerService, accountName));
    }

    [HttpPatch]
    [KeycloakAuthorize(Policies.READ_All_NOTES, Policies.EDIT_ALL_NOTES)]
    [Route("private/{pathNote}")]
    [ProducesResponseType<NoteEditContentResponseDto>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    public IActionResult EditNoteAll([FromRoute] string accountName,
        [FromRoute] string pathNote,
        [FromBody] NoteEditContentRequestDto editDto)
    {
        return Execute(() => um.OfEditContent(editService.EditContentNote(pathNote, editDto)),
            new EqualNameP(registerService, accountName));
    }

    [HttpPost]
    [KeycloakAuthorize(Policies.READ_All_NOTES, Policies.EDIT_ALL_NOTES)]
    [Route("private/{pathNote}/create")]
    [ProducesResponseType<NoteEditContentResponseDto>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    public IActionResult CreateNote([FromRoute] string accountName,
        [FromRoute] string pathNote,
        [FromBody] NoteEditContentRequestDto editDto)
    {
        return Execute(() => um.OfEditContent(editService.EditContentNote(pathNote, editDto)),
            new EqualNameP(registerService, accountName));
    }

    [HttpGet]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.SEARCH_OWN_NOTES)]
    [Route("search")]
    [ProducesResponseType<List<NoteSearchResponseDto>>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    public IActionResult SearchByTitleNotes([FromRoute] string accountName,
        [FromBody] NoteSearchRequestDto noteSearch)
    {
        return Execute(() => um.OfSearch(noteInformationService.Find(noteSearch.Title, accountName)),
            new EqualNameP(registerService, accountName));
    }

    [HttpGet]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.SEARCH_OWN_NOTES)]
    [ProducesResponseType<List<NoteSearchResponseDto>>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [Route("semantic_search")]
    public IActionResult SemanticSearchNotes([FromRoute] string accountName,
        [FromBody] NoteSemanticSearchRequestDto noteSearch)
    {
        return Execute(() => um.OfSearch(
                noteInformationService.FindSemantic(accountName, noteSearch.Query)),
            new EqualNameP(registerService, accountName));
    }

    [HttpGet]
    [KeycloakAuthorize(Policies.READ_NOTES)]
    [ProducesResponseType<List<NoteSearchResponseDto>>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    public IActionResult Notes([FromRoute] string accountName)
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
    public IActionResult GetNote([FromRoute] string accountName,
        [FromRoute] string pathNote)
    {
        return Execute(() => um.OfContentSearch(noteInformationService.GetFull(pathNote, accountName)),
            new EqualNameP(registerService, accountName));
    }

    [HttpGet]
    [KeycloakAuthorize(Policies.READ_All_NOTES)]
    [Route("private/{pathNote}")]
    [ProducesResponseType<NoteCreateResponseDto>(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status403Forbidden)]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    public IActionResult GetNoteAll([FromRoute] string accountName,
        [FromBody] NoteCreateRequestDto noteCreate)
    {
        return Execute(() => um.OfCreateNote(editService.CreateNote(accountName, noteCreate)),
            new EqualNameP(registerService, accountName));
    }
}