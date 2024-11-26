using LR.model;
using Microsoft.EntityFrameworkCore;

namespace LR.repositories;

public sealed class AccountRepositoryDb(DbContextOptions<AccountRepositoryDb> options)
    : DbContext(options), IAccountRepository
{
    public DbSet<Account> Accounts { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Account>()
            .Property(e => e.Role)
            .HasConversion<string>();
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
        return a;
    }

    public Account? FindByName(string name)
    {
        return Accounts.FirstOrDefault(a => a.Name == name);
    }

    public Account Detach(Account account)
    {
        Attach(account);
        var e =  Entry(account);
        e.State = EntityState.Detached;
        SaveChanges();
        return e.Entity;
    }
}