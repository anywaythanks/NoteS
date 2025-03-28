using System.ComponentModel.DataAnnotations;
using System.Text.Json;
using Microsoft.AspNetCore.Diagnostics;
using Microsoft.AspNetCore.Mvc;

namespace NoteS.exceptions.handlers;

public class GlobalExceptionHandler(ILogger<GlobalExceptionHandler> logger) : IExceptionHandler
{
    public async ValueTask<bool> TryHandleAsync(
        HttpContext context,
        Exception exception,
        CancellationToken cancellationToken)
    {
        logger.LogInformation(exception, "An exception occurred");

        var (statusCode, title, type) = MapExceptionToStatusCode(exception);
        var problemDetails = new ProblemDetails
        {
            Status = statusCode,
            Title = title,
            Detail = exception.Message,
            Type = type
        };

        context.Response.StatusCode = statusCode;
        context.Response.ContentType = "application/json";
        await context.Response.WriteAsync(
            JsonSerializer.Serialize(problemDetails),
            cancellationToken
        );

        return true;
    }

    private static (int statusCode, string msg, string type) MapExceptionToStatusCode(Exception ex)
    {
        return ex switch
        {
            UnauthorizedAccessException => (StatusCodes.Status403Forbidden, "Forbidden", "403"),
            KeyNotFoundException => (StatusCodes.Status404NotFound, "Not Found", "404"),
            ValidationException => (StatusCodes.Status400BadRequest, "Validation Error", "400"),
            StatusCodeException sc => (sc.StatusCode, sc.Title, InternalCode: sc.InternalCode),
            _ => (StatusCodes.Status500InternalServerError, "Internal Server Error", "500")
        };
    }
}