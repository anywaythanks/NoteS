using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using NoteS.Attributes;
using NoteS.Models;

namespace NoteS.controllers;

[ApiController]
[Route("api/private/notes")]
public class PrivateNoteController() : Controller
{
    [HttpPut]
    [KeycloakAuthorize(Policies.EDIT_OWN_NOTES)]
    [Route("{pathNote}")]
    public IResult EditNote([FromRoute] string pathNote /*TODO: Пусть будет автогенерация GUID, потом юзер меняет*/,
        [FromBody] Object noteDto/*TODO: Дто с основными данными, типа блоков, которые надо добавить*/)
    {//TODO: DTO заметка ЮЗЕРА с контентом.
        return null!;
    }
    [HttpPatch]
    [KeycloakAuthorize(Policies.EDIT_OWN_NOTES)]
    [Route("{pathNote}")]
    public IResult EditBlocks([FromRoute] string pathNote /*TODO: Пусть будет автогенерация GUID, потом юзер меняет*/,
        [FromBody] Object noteBlocksDto/*TODO: Дто с уже сущствующей заметкой и отмеченными измененными блоками*/)
    {//TODO: DTO заметка ЮЗЕРА с контентом.
        return null!;
    }
    
    [HttpGet]
    [KeycloakAuthorize(Policies.SEARCH_OWN_NOTES)]
    [Route("search")]
    public IResult SearchNotes([FromQuery] String query)//TODO: Це выборка из эластика
    //TODO: List DTO заметок ЮЗЕРА без контента.
    {
        return null!;
    }
    [HttpGet]
    [KeycloakAuthorize(Policies.SEARCH_OWN_NOTES)]
    [Route("semantic_search")]
    public IResult SemanticSearchNotes([FromQuery] String query)//TODO: Це выборка из эластика
    //TODO: List DTO заметок ЮЗЕРА без контента.
    {
        return null!;
    }
    
    [HttpGet]
    [KeycloakAuthorize(Policies.READ_NOTES)]
    public IResult Notes() //TODO: List DTO заметок ЮЗЕРА без контента.
    {
        return null!;
    }

    [HttpGet]
    [KeycloakAuthorize(Policies.READ_NOTES)]
    [Route("{pathNote}")]
    public IResult GetNote([FromRoute] string pathNot)
    //TODO: DTO заметка ЮЗЕРА с контентом.
    {
        return null!;
    }
}