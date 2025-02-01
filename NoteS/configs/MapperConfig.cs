using AccountMapper = NoteS.mappers.AccountMapper;

namespace NoteS.configs;

public class MapperConfig
{
    public static void Configuration(IHostApplicationBuilder builder)
    {
        builder.Services.AddTransient<AccountMapper>();
    }
}