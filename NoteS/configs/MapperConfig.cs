using NoteS.models.mappers;

namespace NoteS.configs;

public class MapperConfig
{
    public static void Configuration(IHostApplicationBuilder builder)
    {
        builder.Services.AddTransient<UniversalDtoMapper>();
        builder.Services.AddTransient<AccountMapper>();
        builder.Services.AddTransient<TagMapper>();
        builder.Services.AddTransient<NoteMapper>();
    }
}