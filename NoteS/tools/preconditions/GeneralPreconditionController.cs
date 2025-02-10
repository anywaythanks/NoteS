using Microsoft.AspNetCore.Mvc;
using NoteS.exceptions;

namespace NoteS.tools.preconditions;

public abstract class GeneralPreconditionController(params IGeneralPrecondition[] generalPreconditions) : Controller
{
    protected async Task<IActionResult> Execute<T>(Func<T> f, params IGeneralPrecondition[] generalPreconditionsIntern)
    {
        return await ExecuteA(() => Task.FromResult<IActionResult>(Ok(f())), generalPreconditionsIntern);
    }

    protected async Task<IActionResult> ExecuteA(Func<Task<IActionResult>> f,
        params IGeneralPrecondition[] generalPreconditionsIntern)
    {
        var mess = "Что-то пошло не так.";
        try
        {
            var sid = User.FindFirst("sid")?.Value ?? throw new NotFound("пользователь");
            foreach (var precondition in generalPreconditions)
            {
                if (!precondition.Check(User.Identity, new(sid))) return BadRequest(mess);
            }

            foreach (var precondition in generalPreconditionsIntern)
            {
                if (!precondition.Check(User.Identity, new(sid))) return BadRequest(mess);
            }

            return await f();
        }
        catch (StatusCodeException se)
        {
            return se.Result;
        }
    }
}