using Microsoft.EntityFrameworkCore;
using NoteS.repositories;

namespace NoteS.configs;

public class RepositoriesConfig
{
    public static void Configuration(IHostApplicationBuilder builder)
    {
        var configuration = builder.Configuration;
        var db = () =>
            $"Host={configuration["Database:host"]};" +
            $"Port={configuration["Database:port"]};" +
            $"Database={configuration["Database:db"]};" +
            $"Username={configuration["Database:username"]};" +
            $"Password={configuration["Database:password"]};";

        builder.Services.AddDbContext<IAccountRepository, AccountRepositoryDb>(options =>
            options.UseNpgsql(db()).EnableSensitiveDataLogging());
        builder.Services.AddDbContext<INoteRepository, NoteRepositoryDbAndElastic>(options =>
            options.UseNpgsql(db()).EnableSensitiveDataLogging());
        builder.Services.AddDbContext<ITagRepository, TagRepositoryDb>(options =>
            options.UseNpgsql(db()).EnableSensitiveDataLogging());
    }

    public static void AfterConfiguration(WebApplication app)
    {
        using (var scope = app.Services.CreateScope())
        {
            scope.ServiceProvider.GetRequiredService<AccountRepositoryDb>().Database.EnsureCreated();
            scope.ServiceProvider.GetRequiredService<NoteRepositoryDbAndElastic>().Database.EnsureCreated();
            scope.ServiceProvider.GetRequiredService<TagRepositoryDb>().Database.EnsureCreated();
        }
    }
}