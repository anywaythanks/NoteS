using NoteS.models.mappers;

namespace NoteS.configs;

public class MapperConfig
{
    public static void Configuration(IHostApplicationBuilder builder)
    {
        builder.Services.AddTransient<UniversalMapper>();
    }
}