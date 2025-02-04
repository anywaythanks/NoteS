using Microsoft.EntityFrameworkCore;
using NoteS.repositories;

namespace NoteS.configs;

public class RepositoriesConfig
{
    public static void Configuration(IHostApplicationBuilder builder)
    {
        var configuration = builder.Configuration;
        var db = "Host=localhost;Port=5432;Database=NoteS_db;Username=anyway;Password=anyway;";
        
        builder.Services.AddDbContext<IAccountRepository, AccountRepositoryDb>(options =>
            options.UseNpgsql(db).EnableSensitiveDataLogging());
        builder.Services.AddDbContext<INoteRepository, NoteRepositoryDb>(options =>
            options.UseNpgsql(db).EnableSensitiveDataLogging());
        builder.Services.AddDbContext<ITagRepository, TagRepositoryDb>(options =>
            options.UseNpgsql(db).EnableSensitiveDataLogging());
    }
    
    public static void AfterConfiguration(WebApplication app)
    {
        using (var scope = app.Services.CreateScope())
        {
            scope.ServiceProvider.GetRequiredService<AccountRepositoryDb>().Database.EnsureCreated();
            scope.ServiceProvider.GetRequiredService<NoteRepositoryDb>().Database.EnsureCreated();
            scope.ServiceProvider.GetRequiredService<TagRepositoryDb>().Database.EnsureCreated();
        }
    }
}