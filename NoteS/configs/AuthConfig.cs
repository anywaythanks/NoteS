using System.Security.Claims;
using System.Text.Json;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authorization;
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
            .AddAuthentication(JwtBearerDefaults.AuthenticationScheme)
            .AddJwtBearer(x =>
            {
                x.RequireHttpsMetadata = Convert.ToBoolean($"{configuration["Keycloak:require-https"]}");
                x.MetadataAddress =
                    $"{configuration["Keycloak:server-url"]}/realms/{configuration["Keycloak:realm"]}/.well-known/openid-configuration";
                x.TokenValidationParameters = new TokenValidationParameters
                {
                    RoleClaimType = ClaimTypes.Role,
                    NameClaimType = "preferred_username",
                    ValidAudience = $"{configuration["Keycloak:audience"]}",
                    // https://stackoverflow.com/questions/60306175/bearer-error-invalid-token-error-description-the-issuer-is-invalid
                    ValidateIssuer = Convert.ToBoolean($"{configuration["Keycloak:validate-issuer"]}"),
                };
                x.TokenValidationParameters.NameClaimType = "preferred_username";

                // Map "sid" claim (no extra work needed if it's already in the token)
                x.TokenValidationParameters.ValidateAudience = true;

                x.Events = new JwtBearerEvents
                {
                    OnTokenValidated = context =>
                    {
                        if (context.Principal?.Identity is ClaimsIdentity identity)
                        {
                            var realmAccess = context.Principal.FindFirst("realm_access")?.Value;
                            if (!string.IsNullOrEmpty(realmAccess))
                            {
                                var realmAccessObj = JsonDocument.Parse(realmAccess);
                                if (realmAccessObj.RootElement.TryGetProperty("roles", out var roles))
                                {
                                    foreach (var role in roles.EnumerateArray())
                                    {
                                        identity.AddClaim(new Claim(ClaimTypes.Role, role.GetString() ?? string.Empty));
                                    }
                                }
                            }
                        }

                        return Task.CompletedTask;
                    },
                    OnChallenge = context =>
                    {
                        context.HandleResponse();
                        context.Response.StatusCode = StatusCodes.Status401Unauthorized;
                        return Task.CompletedTask;
                    },
                    OnForbidden = context =>
                    {
                        context.Response.StatusCode = StatusCodes.Status403Forbidden;
                        return Task.CompletedTask;
                    }
                };
                x.MapInboundClaims = false;
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
                policy => { policy.WithOrigins("http://localhost:8080"); });
        });
    }

    public static void AfterConfiguration(WebApplication app)
    {
        app.UseAuthentication();
        app.UseAuthorization();
        app.UseCors("MyAllowSpecificOrigins");
    }
}