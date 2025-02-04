using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.OpenApi.Any;
using Microsoft.OpenApi.Interfaces;
using Microsoft.OpenApi.Models;

namespace NoteS.configs;

public class SwaggerConfig
{
    public static void Configuration(IHostApplicationBuilder builder)
    {
        builder.Services.AddEndpointsApiExplorer();
        var configuration = builder.Configuration;

        builder.Services.AddSwaggerGen(setup =>
        {
            var exts = new Dictionary<string, IOpenApiExtension>();
            var jwtSecurityScheme = new OpenApiSecurityScheme()
            {
                BearerFormat = "JWT",
                Name = "JWT Authentication",
                Type = SecuritySchemeType.OAuth2,
                Scheme = JwtBearerDefaults.AuthenticationScheme,
                Description = "API использует [OAuth2 authorizationCode]" +
                              "(https://auth0.com/docs/get-started/authentication-and-authorization-flow/authorization-code-flow).",
                Flows = new OpenApiOAuthFlows
                {
                    AuthorizationCode = new OpenApiOAuthFlow
                    {
                        AuthorizationUrl = new Uri($"{configuration["Keycloak:server-url"]}" +
                                                   $"/realms/{configuration["Keycloak:name_claim"]}" +
                                                   $"/protocol/openid-connect/auth"),
                        TokenUrl = new Uri($"{configuration["Keycloak:server-url"]}" +
                                           $"/realms/{configuration["Keycloak:name_claim"]}" +
                                           $"/protocol/openid-connect/token"),
                        Scopes = new Dictionary<string, string>
                        {
                            { "read" , "Balea Server HTTP Api" }
                        }
                    }
                },
                Reference = new OpenApiReference
                {
                    Id = JwtBearerDefaults.AuthenticationScheme,
                    Type = ReferenceType.SecurityScheme
                }
            };
            setup.EnableAnnotations();
            setup.SwaggerDoc("v1", new OpenApiInfo { Title = "NoteS API", Version = "v1" });
            setup.AddSecurityDefinition(jwtSecurityScheme.Reference.Id, jwtSecurityScheme);

            setup.AddSecurityRequirement(new OpenApiSecurityRequirement
            {
                { jwtSecurityScheme, Array.Empty<string>() }
            });
        });
    }

    public static void AfterConfiguration(WebApplication app)
    {
        var configuration = app.Configuration;
        if (app.Environment.IsDevelopment())
        {
            app.UseSwagger();
            app.UseSwaggerUI(options =>
            {
                options.OAuthClientId($"{configuration["Keycloak:Swagger:Client"]}");
                options.OAuthClientSecret(configuration["Keycloak:Swagger:Secret"]);
                options.DisplayRequestDuration();
            });
        }

        app.MapControllers()
            .WithOpenApi();
    }
}