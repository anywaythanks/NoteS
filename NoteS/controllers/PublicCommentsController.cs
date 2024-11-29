using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace NoteS.controllers;

[ApiController]
[Route("api/public")]
public class PublicCommentsController() : Controller
{
    [HttpGet]
    [AllowAnonymous]
    [Route("notes/{pathNote}/comments")]
    public IResult Comments([FromRoute] string pathNote)
    {
        //TODO: List DTO комментариев к заметке
        return null!;
    }
    [HttpGet]
    [AllowAnonymous]
    [Route("comments/{pathComment}")]
    public IResult GetComment([FromRoute] string pathComment)
    {
        //TODO: Контент комментария, просто возможность по ссылке перейти к комментарию, сохранить там мейби
        return null!;
    }
}