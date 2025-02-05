using System.Diagnostics.CodeAnalysis;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.ChangeTracking;
using NoteS.Models;

namespace NoteS.repositories;

public sealed class AccountRepositoryDb(DbContextOptions<AccountRepositoryDb> options)
    : DbContext(options), IAccountRepository
{
    public DbSet<Account> Accounts { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        // modelBuilder.Entity<Account>()
        //     .Property(e => e.Role)
        //     .HasConversion<string>();
    }

    public Account Save(Account account)
    {
        var a = account;
        if (account.Id != null)
        {
            Accounts.Add(a);
            Accounts.Update(a);
        }
        else
        {
            a = Accounts.Add(account).Entity;
        }

        SaveChanges();
        return Detach(a);
    }

    public Account? FindByName(string name)
    {
        return Detach(Accounts.FirstOrDefault(a => a.Name == name));
    }

    public Account? FindByUuid(string uuid)
    {
        return Detach(Accounts.FirstOrDefault(a => a.Uuid == uuid));
    }

    [return: NotNullIfNotNull(nameof(account))]
    private Account? Detach(Account? account)
    {
        if (account == null) return null;
        Attach(account);
        EntityEntry<Account> e = Entry(account);
        e.State = EntityState.Detached;
        SaveChanges();
        return e.Entity;
    }
}