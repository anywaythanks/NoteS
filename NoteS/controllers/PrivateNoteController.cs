using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using NoteS.tools.preconditions;

namespace NoteS.controllers;

[ApiController]
[Route("api/{account_name}/notes")]
public class PrivateNoteController(EqualNameP equalNameP) : GeneralPreconditionController(equalNameP)
{
    [HttpPut]
    [Authorize(Policy = "edit_own_notes")]
    //TODO: что типа проверки, что ты можешь редактировать own ноты и ты own или ты просто вседозволено можешь менять все. 
    [Route("{pathNote}")]
    public IResult EditNote([FromRoute] string accountName,
        [FromRoute] string pathNote /*TODO: Пусть будет автогенерация GUID, потом юзер меняет*/,
        [FromBody] Object noteDto /*TODO: Дто с основными данными, типа блоков, которые надо добавить*/)
    {
        //TODO: DTO заметка ЮЗЕРА с контентом.
        return Execute<>(accountName, null);
    }

    [HttpPatch]
    [Authorize(Policy = "edit_own_notes")]
    [Route("{pathNote}")]
    public IResult EditBlocks([FromRoute] string accountName,
        [FromRoute] string pathNote /*TODO: Пусть будет автогенерация GUID, потом юзер меняет*/,
        [FromBody] Object noteBlocksDto /*TODO: Дто с уже сущствующей заметкой и отмеченными измененными блоками*/)
    {
        //TODO: DTO заметка ЮЗЕРА с контентом.
        return Execute<>(accountName, null);
    }

    [HttpGet]
    [Authorize(Policy = "search_own_notes")]
    [Route("search")]
    public IResult SearchNotes([FromRoute] string accountName,
            [FromQuery] String query) //TODO: Це выборка из эластика
        //TODO: List DTO заметок ЮЗЕРА без контента.
    {
        return Execute<>(accountName, null);
    }

    [HttpGet]
    [Authorize(Policy = "search_own_notes")]
    [Route("semantic_search")]
    public IResult SemanticSearchNotes([FromRoute] string accountName,
            [FromQuery] String query) //TODO: Це выборка из эластика
        //TODO: List DTO заметок ЮЗЕРА без контента.
    {
        return Execute<>(accountName, null);
    }

    [HttpGet]
    [Authorize(Policy = "read_notes")]
    public IResult Notes([FromRoute] string accountName) //TODO: List DTO заметок ЮЗЕРА без контента.
    {
        return Execute<>(accountName, null);
    }

    [HttpGet]
    [Authorize(Policy = "read_notes")]
    [Route("{pathNote}")]
    public IResult GetNote([FromRoute] string accountName,
            [FromRoute] string pathNot)
        //TODO: DTO заметка ЮЗЕРА с контентом.
    {
        return Execute<>(accountName, null);
    }
}