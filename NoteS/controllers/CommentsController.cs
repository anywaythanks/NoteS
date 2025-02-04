using Microsoft.AspNetCore.Mvc;
using NoteS.Attributes;
using NoteS.Models;
using NoteS.tools.preconditions;

namespace NoteS.controllers;

[ApiController]
[Route("api/public/{accountName}/notes/{pathNote}/comments")]
public class CommentsController  : GeneralPreconditionController
{
    
    [HttpGet]
    [KeycloakAuthorize(Policies.READ_COMMENTS)]
    public IResult Comments([FromRoute] string pathNote) //TODO: List DTO комментариев(Type:Comments) c контентом. 
    {
        return null!;
    }
}