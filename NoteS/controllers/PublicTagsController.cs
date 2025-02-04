using Microsoft.AspNetCore.Mvc;
using NoteS.Attributes;
using NoteS.Models;
using NoteS.tools.preconditions;

namespace NoteS.controllers;

[ApiController]
[Route("api/public/{accountName}/tags")]
public class PublicTagsController : GeneralPreconditionController
{
    [HttpGet]
    [KeycloakAuthorize(Policies.READ_NOTES)]
    [Route("{pathNote}")]
    public IResult Tags([FromRoute] string accountName,
        [FromRoute] string pathNote)
    {
        return null!;
    }

    [HttpPost]
    [KeycloakAuthorize(Policies.CREATE_NOTES)]
    public IResult AddTag([FromRoute] string accountName)
    {
        return null!;
    }

    [HttpGet]
    [KeycloakAuthorize(Policies.READ_NOTES)]
    public IResult Tags([FromRoute] string accountName)
    {
        return null!;
    }

    [HttpDelete]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.DELETE_NOTES)]
    [Route("{pathNote}")]
    public IResult DelTag([FromRoute] string accountName, [FromRoute] string pathNote)
    {
        return null!;
    }

    [HttpPost]
    [KeycloakAuthorize(Policies.READ_NOTES, Policies.CREATE_NOTES)]
    [Route("{pathNote}")]
    public IResult AddTag([FromRoute] string accountName, [FromRoute] string pathNote)
    {
        return null!;
    }
}