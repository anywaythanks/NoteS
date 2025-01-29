using NoteS.configs;

var builder = WebApplication.CreateBuilder(args);
ControllersConfig.Configuration(builder);
LoggerConfig.Configuration(builder);
MapperConfig.Configuration(builder);
ServicesConfig.Configuration(builder);
AuthConfig.Configuration(builder);
RepositoriesConfig.Configuration(builder);
SwaggerConfig.Configuration(builder);

var app = builder.Build();
RepositoriesConfig.AfterConfiguration(app);
SwaggerConfig.AfterConfiguration(app);

app.Run();