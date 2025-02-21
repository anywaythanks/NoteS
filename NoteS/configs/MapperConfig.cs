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
        builder.Services.AddTransient<PageMapper>();
    }

    public static void AfterConfiguration(WebApplication app)
    {
        using (var scope = app.Services.CreateScope())
        {
            MappersInstances.um = scope.ServiceProvider.GetRequiredService<UniversalDtoMapper>();
            MappersInstances.am = scope.ServiceProvider.GetRequiredService<AccountMapper>();
            MappersInstances.tm = scope.ServiceProvider.GetRequiredService<TagMapper>();
            MappersInstances.nm = scope.ServiceProvider.GetRequiredService<NoteMapper>();
            MappersInstances.pm = scope.ServiceProvider.GetRequiredService<PageMapper>();
        }
    }
}