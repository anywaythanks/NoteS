using LR.model;
using Microsoft.EntityFrameworkCore;
using NoteS.Models;

namespace LR.repositories;

public sealed class ProductRepositoryDb(
    AccountRepositoryDb accountRepositoryDb,
    DbContextOptions<ProductRepositoryDb> options)
    : DbContext(options), IProductRepository
{
    private DbSet<Product> Products { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Product>()
            .HasOne(p => p.Owner)
            .WithMany()
            .HasForeignKey("account_id");
        // modelBuilder.Entity<Account>()
        //     .Property(e => e.Role)
        //     .HasConversion<string>();
    }

    public Product Save(Product product)
    {
        var a = product;
        // Products.AsNoTracking();
        // a.Owner = accountRepositoryDb.Attach(a.Owner).Entity;
        // accountRepositoryDb.SaveChanges();
        a = Products.Attach(a).Entity;
        SaveChanges();
        // a.Owner = accountRepositoryDb.Detach(a.Owner);
        return a;
    }

    public Product Delete(Product product)
    {
        var a = product;
        /*Products.AsNoTracking();*/
        // a.Owner = accountRepositoryDb.Attach(a.Owner).Entity;
        // accountRepositoryDb.SaveChanges();
        a = Products.Remove(a).Entity;
        SaveChanges();
        // a.Owner = accountRepositoryDb.Detach(a.Owner);
        return a;
    }

    public Product? FindByName(string name)
    {
        return (from p in Products.Include(p => p.Owner)
            where p.Name == name
            select p).FirstOrDefault();
    }

    public Product Detach(Product product)
    {
        Entry(product).State = EntityState.Detached;
        Entry(product.Owner).State = EntityState.Detached;
        SaveChanges();
        return product;
    }

    List<Product> IProductRepository.Products()
    {
        return (from p in Products.Include(p => p.Owner)
            select p).ToList();
    }
}