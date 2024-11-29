using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using LR.mappers;
using LR.model;
using LR.repositories;
using LR.services;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using Microsoft.OpenApi.Models;
using Npgsql;

var builder = WebApplication.CreateBuilder(args);
var db = "Host=localhost;Port=5432;Database=applec;Username=anyway;";
builder.Services.AddAuthentication(JwtBearerDefaults.AuthenticationScheme)
    .AddJwtBearer(options =>
    {
        options.TokenValidationParameters = new TokenValidationParameters()
        {
            // указывает, будет ли валидироваться издатель при валидации токена
            ValidateIssuer = true,
            // строка, представляющая издателя
            ValidIssuer = AuthOptions.ISSUER,
            // будет ли валидироваться потребитель токена
            ValidateAudience = true,
            // установка потребителя токена
            ValidAudience = AuthOptions.AUDIENCE,
            // будет ли валидироваться время существования
            ValidateLifetime = true,
            // установка ключа безопасности
            IssuerSigningKey = AuthOptions.GetSymmetricSecurityKey(),
            // валидация ключа безопасности
            ValidateIssuerSigningKey = true,
        };
    }); // подключение аутентификации с помощью jwt-токенов
builder.Services.AddAuthorization();


// Add services to the container.
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen(setup =>
{
    // Include 'SecurityScheme' to use JWT Authentication
    var jwtSecurityScheme = new OpenApiSecurityScheme()
    {
        BearerFormat = "JWT",
        Name = "JWT Authentication",
        In = ParameterLocation.Header,
        Type = SecuritySchemeType.Http,
        Scheme = JwtBearerDefaults.AuthenticationScheme,
        Description = "Put **_ONLY_** your JWT Bearer token on textbox below!",

        Reference = new OpenApiReference
        {
            Id = JwtBearerDefaults.AuthenticationScheme,
            Type = ReferenceType.SecurityScheme
        }
    };

    setup.AddSecurityDefinition(jwtSecurityScheme.Reference.Id, jwtSecurityScheme);

    setup.AddSecurityRequirement(new OpenApiSecurityRequirement
    {
        { jwtSecurityScheme, Array.Empty<string>() }
    });
});
builder.Services.AddControllers();

builder.Services.AddTransient<AccountMapper>();
builder.Services.AddTransient<ProductMapper>();
builder.Services.AddTransient<PurchasesMapper>();

builder.Services.AddTransient<AccountInformationService>();
builder.Services.AddTransient<AccountRegisterService>();
builder.Services.AddTransient<AccountReplenishService>();
builder.Services.AddTransient<ProductDeleteService>();
builder.Services.AddTransient<ProductInformationService>();
builder.Services.AddTransient<ProductRegisterService>();
builder.Services.AddTransient<PurchasesInformationService>();
builder.Services.AddTransient<ProductSellerService>();
builder.Services.AddTransient<PurchasesRegisterService>();
builder.Services.AddTransient<LoginService>();

builder.Services.AddSingleton(new PasswordHasher<Account>());

builder.Services.AddDbContext<IAccountRepository, AccountRepositoryDb>(options =>
    options.UseNpgsql(db).EnableSensitiveDataLogging());
builder.Services.AddDbContext<IProductRepository, ProductRepositoryDb>(options =>
    options.UseNpgsql(db).EnableSensitiveDataLogging());
builder.Services.AddDbContext<IPurchasesRepository, PurchasesRepositoryDb>(options =>
    options.UseNpgsql(db).EnableSensitiveDataLogging());
var app = builder.Build();
app.UseAuthentication();
app.UseAuthorization();

// get the context from the service collection
using (var scope = app.Services.CreateScope())
{
    scope.ServiceProvider.GetRequiredService<AccountRepositoryDb>().Database.EnsureCreated();
    scope.ServiceProvider.GetRequiredService<ProductRepositoryDb>().Database.EnsureCreated();
    scope.ServiceProvider.GetRequiredService<PurchasesRepositoryDb>().Database.EnsureCreated();
}

if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.MapControllers()
    .WithOpenApi();

app.Run();

class AuthOptions
{
    public const string ISSUER = "MyAuthServer"; // издатель токена
    public const string AUDIENCE = "MyAuthClient"; // потребитель токена
    const string KEY = "mysupersecret_secretsecretsecretkey!123"; // ключ для шифрации

    public static SymmetricSecurityKey GetSymmetricSecurityKey() => new(Encoding.UTF8.GetBytes(KEY));
}