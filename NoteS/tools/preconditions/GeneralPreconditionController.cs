using System.Security.Claims;
using Microsoft.AspNetCore.Mvc;
using NoteS.exceptions;

namespace NoteS.tools.preconditions;

public abstract class GeneralPreconditionController(params IGeneralPrecondition[] generalPreconditions) : Controller
{
    protected IActionResult Execute<T>(Func<T> f, params IGeneralPrecondition[] generalPreconditionsIntern)
    {
        return ExecuteA(() => Ok(f()), generalPreconditionsIntern);
    }

    protected IActionResult ExecuteA(Func<IActionResult> f, params IGeneralPrecondition[] generalPreconditionsIntern)
    {
        var mess = "Что-то пошло не так.";
        try
        {
            var uuid = User.FindFirstValue("uuid") ?? throw new NotFound("пользователь");
            foreach (var precondition in generalPreconditions)
            {
                if (!precondition.Check(User.Identity, uuid)) return BadRequest(mess);
            }

            foreach (var precondition in generalPreconditionsIntern)
            {
                if (!precondition.Check(User.Identity, uuid)) return BadRequest(mess);
            }

            return f();
        }
        catch (StatusCodeException se)
        {
            return se.Result;
        }
    }
}