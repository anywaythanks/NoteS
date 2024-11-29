using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace NoteS.controllers;

[ApiController]
[Route("api/private")]
public class PrivateCommentsController() : Controller
{
    [HttpPut]
    [Authorize(Policy = "edit_own_comments")]
    [Route("comments/{pathComment}")]
    public IResult EditComment([FromRoute] string pathComment /*TODO: Пусть будет автогенерация GUID*/,
        [FromBody] Object noteDto/*TODO: Дто с основными данными, типа блоков, которые надо добавить*/)
    {//TODO: DTO заметка ЮЗЕРА с контентом.
        return null!;
    }
    
    [HttpGet]
    [Authorize(Policy = "read_comments")]
    [Route("comments")]
    public IResult Comments() //TODO: List DTO собственных комментариев
    {
        return null!;
    }
}