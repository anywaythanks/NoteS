using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace NoteS.controllers;

[ApiController]
[Route("api/public/notes")]
public class PublicNoteController() : Controller
{
    [HttpGet]
    [AllowAnonymous]
    public IResult Notes()//TODO: List DTO заметок без контента.
    {
        return null!;
    }
    [HttpGet]
    [AllowAnonymous]
    [Route("{pathNote}")]
    public IResult GetNote([FromRoute] string pathNote)//TODO: DTO заметка с контентом.
    {
        return null!;
    }
    
}