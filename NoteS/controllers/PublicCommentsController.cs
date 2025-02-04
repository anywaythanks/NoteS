using Microsoft.AspNetCore.Mvc;
using NoteS.Attributes;
using NoteS.Models;
using NoteS.tools.preconditions;

namespace NoteS.controllers;

[ApiController]
[Route("api/public/{accountName}/notes/{pathNote}/comments")]
public class PublicCommentsController : GeneralPreconditionController
{
    [HttpGet]
    [KeycloakAuthorize(Policies.READ_COMMENTS)]
    public IResult Comments([FromRoute] string accountName,
        [FromRoute] string pathNote)
    {
        return null!;
    }

    [HttpPost]
    [KeycloakAuthorize(Policies.READ_COMMENTS, Policies.READ_NOTES, Policies.CREATE_COMMENTS)]
    public IResult CreateComment([FromRoute] string accountName,
        [FromRoute] string pathNote)
    {
        return null!;
    }

    [HttpPost]
    [KeycloakAuthorize(Policies.READ_COMMENTS, Policies.READ_NOTES, Policies.EDIT_OWN_COMMENTS)]
    [Route("{pathComment}")]
    public IResult EditComment([FromRoute] string accountName,
        [FromRoute] string pathNote,
        [FromRoute] string pathComment) //TODO: Тут наверн надо судить по created_on, мол если разница между больше N, то усе, плаки плаки 
    {
        return null!;
    }
}