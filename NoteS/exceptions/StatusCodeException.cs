using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Infrastructure;

namespace NoteS.exceptions;

public abstract class StatusCodeException : Exception
{
    public IActionResult Result { get; }

    protected StatusCodeException([ActionResultStatusCode] int statusCode)
    {
        Result = new StatusCodeResult(statusCode);
    }
}