using System.Reflection;
using log4net.Config;

namespace NoteS.configs;

public class LoggerConfig
{
    public static void Configuration(IHostApplicationBuilder builder)
    {
        XmlConfigurator.Configure(new FileInfo("log4net.config"));
        var loggerFactory = LoggerFactory.Create(builder => builder.AddLog4Net());
        builder.Services.AddSingleton(loggerFactory);
    }
}