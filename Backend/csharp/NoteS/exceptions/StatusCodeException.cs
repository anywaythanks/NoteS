using System.Text.Json;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Infrastructure;

namespace NoteS.exceptions;

public abstract class StatusCodeException([ActionResultStatusCode] int statusCode, string internalCode, string title, string message)
    : Exception(message)
{
    public int StatusCode { get; } = statusCode;
    public string Title { get; } = title;
    public string InternalCode { get; } = internalCode;
}