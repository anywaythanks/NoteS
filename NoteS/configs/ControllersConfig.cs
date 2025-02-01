namespace NoteS.configs;

public class ControllersConfig
{
    public static void Configuration(IHostApplicationBuilder builder)
    {
        builder.Services.AddControllers();
    }
}