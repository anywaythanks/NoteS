using Microsoft.IdentityModel.Logging;
using NoteS.configs;

IdentityModelEventSource.ShowPII = true; //TODO: В дебаг бы запихнуть
var builder = WebApplication.CreateBuilder(args);
ControllersConfig.Configuration(builder);
LoggerConfig.Configuration(builder);
MapperConfig.Configuration(builder);
ServicesConfig.Configuration(builder);
SecurityConfig.Configuration(builder);
RepositoriesConfig.Configuration(builder);
SwaggerConfig.Configuration(builder);
ElasticConfig.Configuration(builder);
HandlerConfig.Configuration(builder);

var app = builder.Build();
SecurityConfig.AfterConfiguration(app);
RepositoriesConfig.AfterConfiguration(app);
SwaggerConfig.AfterConfiguration(app);
MapperConfig.AfterConfiguration(app);
HandlerConfig.AfterConfiguration(app);

app.Run();