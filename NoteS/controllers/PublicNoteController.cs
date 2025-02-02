using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace NoteS.controllers;

[ApiController]
[Route("api/notes")]
public class PublicNoteController : Controller
{
    [HttpGet]
    [Authorize(Policy = "READ_NOTES")]
    [Route("")]
    public IResult Notes() //TODO: List DTO заметок(Type:Note) без контента, но с title.
    {
        return null!;
    }

    [HttpGet]
    [Authorize(Policy = "READ_NOTES")]
    [Route("{pathNote}")]
    public IResult GetNote([FromRoute] string pathNote) //TODO: DTO заметка(Type:Note) с контентом.
    {
        return null!;
    }

    [HttpGet]
    [Authorize(Policy = "READ_COMMENTS")]
    [Route("{pathNote}/comments")]
    public IResult Comments([FromRoute] string pathNote) //TODO: List DTO комментариев(Type:Comments) c контентом. 
    {
        return null!;
    }
}