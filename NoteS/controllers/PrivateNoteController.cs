using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace NoteS.controllers;

[ApiController]
[Route("api/private/notes")]
public class PrivateNoteController() : Controller
{
    [HttpPut]
    [Authorize(Policy = "edit_own_notes")]
    [Route("{pathNote}")]
    public IResult EditNote([FromRoute] string pathNote /*TODO: Пусть будет автогенерация GUID, потом юзер меняет*/,
        [FromBody] Object noteDto/*TODO: Дто с основными данными, типа блоков, которые надо добавить*/)
    {//TODO: DTO заметка ЮЗЕРА с контентом.
        return null!;
    }
    [HttpPatch]
    [Authorize(Policy = "edit_own_notes")]
    [Route("{pathNote}")]
    public IResult EditBlocks([FromRoute] string pathNote /*TODO: Пусть будет автогенерация GUID, потом юзер меняет*/,
        [FromBody] Object noteBlocksDto/*TODO: Дто с уже сущствующей заметкой и отмеченными измененными блоками*/)
    {//TODO: DTO заметка ЮЗЕРА с контентом.
        return null!;
    }
    
    [HttpGet]
    [Authorize(Policy = "search_own_notes")]
    [Route("search")]
    public IResult SearchNotes([FromQuery] String query)//TODO: Це выборка из эластика
    //TODO: List DTO заметок ЮЗЕРА без контента.
    {
        return null!;
    }
    [HttpGet]
    [Authorize(Policy = "search_own_notes")]
    [Route("semantic_search")]
    public IResult SemanticSearchNotes([FromQuery] String query)//TODO: Це выборка из эластика
    //TODO: List DTO заметок ЮЗЕРА без контента.
    {
        return null!;
    }
    
    [HttpGet]
    [Authorize(Policy = "read_notes")]
    public IResult Notes() //TODO: List DTO заметок ЮЗЕРА без контента.
    {
        return null!;
    }

    [HttpGet]
    [Authorize(Policy = "read_notes")]
    [Route("{pathNote}")]
    public IResult GetNote([FromRoute] string pathNot)
    //TODO: DTO заметка ЮЗЕРА с контентом.
    {
        return null!;
    }
}