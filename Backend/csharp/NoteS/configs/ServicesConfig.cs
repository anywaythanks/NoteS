using NoteS.services;

namespace NoteS.configs;

public class ServicesConfig
{
    public static void Configuration(IHostApplicationBuilder builder)
    {
        builder.Services.AddTransient<AccountInformationService>();
        builder.Services.AddTransient<AccountRegisterService>();
        builder.Services.AddTransient<CommentEditService>();
        builder.Services.AddTransient<CommentInformationService>();
        builder.Services.AddTransient<NoteEditService>();
        builder.Services.AddTransient<NoteInformationService>();
        builder.Services.AddTransient<TagEditService>();
        builder.Services.AddTransient<TagInformationService>();
    }
}