using Microsoft.IdentityModel.Logging;
using NoteS.configs;

IdentityModelEventSource.ShowPII = true; //TODO: В дебаг бы запихнуть
var builder = WebApplication.CreateBuilder(args);
ControllersConfig.Configuration(builder);
LoggerConfig.Configuration(builder);
MapperConfig.Configuration(builder);
ServicesConfig.Configuration(builder);
AuthConfig.Configuration(builder);
RepositoriesConfig.Configuration(builder);
SwaggerConfig.Configuration(builder);
ElasticConfig.Configuration(builder);

var app = builder.Build();
AuthConfig.AfterConfiguration(app);
RepositoriesConfig.AfterConfiguration(app);
SwaggerConfig.AfterConfiguration(app);

app.Run();