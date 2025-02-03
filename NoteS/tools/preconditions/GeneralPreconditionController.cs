using System.Security.Claims;
using Microsoft.AspNetCore.Mvc;
using NoteS.exceptions;

namespace NoteS.tools.preconditions;

public abstract class GeneralPreconditionController(params IGeneralPrecondition[] generalPreconditions) : Controller
{
    public IActionResult Execute<T>(Func<T> f, params IGeneralPrecondition[] generalPreconditionsIntern)
    {
        try
        {
            var uuid = User.FindFirstValue("uuid") ?? throw new NotFound();
            foreach (var precondition in generalPreconditions)
            {
                if (!precondition.Check(User.Identity, uuid)) return BadRequest();
            }

            foreach (var precondition in generalPreconditionsIntern)
            {
                if (!precondition.Check(User.Identity, uuid)) return BadRequest();
            }
            return Ok(f());
        }
        catch (StatusCodeException se)
        {
            return se.Result;
        }
    }
}