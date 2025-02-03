using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using NoteS.Attributes;
using NoteS.Models;

namespace NoteS.controllers;

[ApiController]
[Route("api/private")]
public class PrivateCommentsController() : Controller
{
    [HttpPut]
    [KeycloakAuthorize(Policies.EDIT_OWN_NOTES)]
    [Route("comments/{pathComment}")]
    public IResult EditComment([FromRoute] string pathComment /*TODO: Пусть будет автогенерация GUID*/,
        [FromBody] Object noteDto/*TODO: Дто с основными данными, типа блоков, которые надо добавить*/)
    {//TODO: DTO заметка ЮЗЕРА с контентом.
        return null!;
    }
    
    [HttpGet]
    [KeycloakAuthorize(Policies.READ_COMMENTS)]
    [Route("comments")]
    public IResult Comments() //TODO: List DTO собственных комментариев
    {
        return null!;
    }
}