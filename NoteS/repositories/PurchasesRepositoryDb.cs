using System.Collections.Concurrent;
using LR.model;
using Microsoft.EntityFrameworkCore;
using NoteS.Models;

namespace LR.repositories;

public class PurchasesRepositoryDb(
    AccountRepositoryDb accountRepositoryDb,
    ProductRepositoryDb productRepositoryDb,
    DbContextOptions<PurchasesRepositoryDb> options)
    : DbContext(options), IPurchasesRepository
{
    private DbSet<Purchase> Purchases { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Product>()
            .HasOne(p => p.Owner)
            .WithMany()
            .HasForeignKey("account_id");
        modelBuilder.Entity<Purchase>()
            .HasOne(p => p.Buyer)
            .WithMany()
            .HasForeignKey("account_id");
        modelBuilder.Entity<Purchase>()
            .HasOne(p => p.Product)
            .WithMany()
            .HasForeignKey("product_id");
        modelBuilder.Entity<Purchase>()
            .Property(p => p.Number)
            .IsRequired(false);
        // modelBuilder.Entity<Account>()
        //     .Property(e => e.Role)
        //     .HasConversion<string>();
    }

    public Purchase Save(Purchase purchases)
    {
        var p = purchases;
        // p.Buyer = accountRepositoryDb.Attach(p.Buyer).Entity;
        // accountRepositoryDb.SaveChanges();
        // p.Product = productRepositoryDb.Attach(p.Product).Entity;
        // productRepositoryDb.SaveChanges();
        p = Purchases.Attach(p).Entity;
        SaveChanges();
        // p.Buyer = accountRepositoryDb.Detach(p.Buyer);
        // p.Product = productRepositoryDb.Detach(p.Product);
        return p;
    }

    public List<Purchase> FindAll()
    {
        return Purchases
            .Include(p => p.Buyer)
            .Include(p => p.Product)
            .Include(p => p.Product.Owner)
            .ToList();
    }

    public Purchase Detach(Purchase purchase)
    {
        Entry(purchase).State = EntityState.Detached;
        Entry(purchase.Buyer).State = EntityState.Detached;
        Entry(purchase.Product).State = EntityState.Detached;
        Entry(purchase.Product.Owner).State = EntityState.Detached;
        SaveChanges();
        return purchase;
    }

    public Purchase? FindByNumber(int number)
    {
        return Purchases
            .Include(p => p.Buyer)
            .Include(p => p.Product)
            .Include(p => p.Product.Owner)
            .FirstOrDefault(a => a.Number == number);
    }
}