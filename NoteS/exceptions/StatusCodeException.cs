using System.Text.Json;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Infrastructure;

namespace NoteS.exceptions;

public abstract class StatusCodeException : Exception
{
    public IActionResult Result { get; }

    protected StatusCodeException([ActionResultStatusCode] int statusCode, string code, string reason)
    {
        Result = new ContentResult
        {
            StatusCode = statusCode,
            Content =JsonSerializer.Serialize(new
            {
                status = statusCode,
                interalCode = code,
                mesage = reason
            }),
            ContentType = "application/json"
        };
    }
}