﻿using LR.repositories;
using Microsoft.EntityFrameworkCore;

namespace NoteS.configs;

public class RepositoriesConfig
{
    public static void Configuration(IHostApplicationBuilder builder)
    {
        var db = "Host=localhost;Port=5432;Database=applec;Username=anyway;";
        
        builder.Services.AddDbContext<IAccountRepository, AccountRepositoryDb>(options =>
            options.UseNpgsql(db).EnableSensitiveDataLogging());
    }
    
    public static void AfterConfiguration(WebApplication app)
    {
        using (var scope = app.Services.CreateScope())
        {
            scope.ServiceProvider.GetRequiredService<AccountRepositoryDb>().Database.EnsureCreated();
        }
    }
}