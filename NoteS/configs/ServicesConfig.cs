using NoteS.services;

namespace NoteS.configs;

public class ServicesConfig
{
    public static void Configuration(IHostApplicationBuilder builder)
    {
        builder.Services.AddTransient<AccountInformationService>();
        builder.Services.AddTransient<AccountRegisterService>();
    }
}