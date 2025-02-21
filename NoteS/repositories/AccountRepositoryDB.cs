using System.Diagnostics.CodeAnalysis;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.ChangeTracking;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using NoteS.models.entity;

namespace NoteS.repositories;

public sealed class AccountRepositoryDb(DbContextOptions<AccountRepositoryDb> options)
    : DbContext(options), IAccountRepository
{
    public DbSet<Account> Accounts { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Account>()
            .Property(e => e.Name)
            .HasConversion(
                new ValueConverter<string, string>(v => v.TrimEnd(), v => v.TrimEnd()));
        modelBuilder.Entity<Account>()
            .Property(e => e.Uuid)
            .HasConversion(
                new ValueConverter<string, string>(v => v.TrimEnd(), v => v.TrimEnd()));
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

    public Account? FindByName(AccNameDto name)
    {
        return Detach(Accounts.FirstOrDefault(a => a.Name == name.Name));
    }

    public Account? FindById(AccIdDto id)
    {
        return Detach(Accounts.FirstOrDefault(a => a.Id == id.Id));
    }

    public Account? FindByUuid(AccUuidDto uuid)
    {
        return Detach(Accounts.FirstOrDefault(a => a.Uuid == uuid.Uuid));
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