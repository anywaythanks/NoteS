using NoteS.exceptions.handlers;

namespace NoteS.configs;

public class HandlerConfig
{
    public static void Configuration(IHostApplicationBuilder builder)
    {
        builder.Services.AddExceptionHandler<GlobalExceptionHandler>();
        builder.Services.AddProblemDetails();
    }

    public static void AfterConfiguration(WebApplication app)
    {
        app.UseExceptionHandler();
    }
}