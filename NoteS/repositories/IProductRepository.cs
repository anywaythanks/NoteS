using LR.model;
using NoteS.Models;

namespace LR.repositories;

public interface IProductRepository
{
    public Product Save(Product product);
    public Product Delete(Product product);
    public Product? FindByName(string name);

    public Product Detach(Product product);
    public List<Product> Products();
}