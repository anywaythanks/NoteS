using LR.model;
using NoteS.Models;

namespace LR.repositories;

public interface IAccountRepository
{
    public Account Save(Account account);
    public Account? FindByName(string name);
    public Account Detach(Account account);
}