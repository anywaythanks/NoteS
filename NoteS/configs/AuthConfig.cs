using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authorization;
using Microsoft.Extensions.Options;
using Microsoft.IdentityModel.Tokens;

namespace NoteS.configs;

public class AuthConfig
{
    //TODO: По хорошему ssl нужен, но пофиг
    public static void Configuration(IHostApplicationBuilder builder)
    {
        // https://stackoverflow.com/questions/77084743/secure-asp-net-core-rest-api-with-keycloak
        var services = builder.Services;
        var configuration = builder.Configuration;
        services
            .AddAuthentication()
            .AddJwtBearer(x =>
            {
                x.RequireHttpsMetadata = Convert.ToBoolean($"{configuration["Keycloak:require-https"]}");
                x.MetadataAddress =
                    $"{configuration["Keycloak:server-url"]}/realms/projects/.well-known/openid-configuration";
                x.TokenValidationParameters = new TokenValidationParameters()
                {
                    RoleClaimType = "groups",
                    NameClaimType = $"{configuration["Keycloak:name_claim"]}",
                    ValidAudience = $"{configuration["Keycloak:audience"]}",
                    // https://stackoverflow.com/questions/60306175/bearer-error-invalid-token-error-description-the-issuer-is-invalid
                    ValidateIssuer = Convert.ToBoolean($"{configuration["Keycloak:validate-issuer"]}"),
                };
            });
        services.AddAuthorization(o =>
        {
            o.DefaultPolicy = new AuthorizationPolicyBuilder()
                .RequireAuthenticatedUser()
                // .RequireClaim("email_verified", "true")
                .Build();
        });
        
        builder.Services.AddCors(options =>
        {
            options.AddPolicy(name: "MyAllowSpecificOrigins",
                policy  =>
                {
                    policy.WithOrigins("http://localhost:8080");
                });
        });

    }
    
    public static void AfterConfiguration(WebApplication app)
    {
        app.UseAuthentication();
        app.UseAuthorization();
        app.UseCors("MyAllowSpecificOrigins");
    }
}