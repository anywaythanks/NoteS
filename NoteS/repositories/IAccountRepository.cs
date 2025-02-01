using NoteS.Models;

namespace NoteS.repositories;

public interface IAccountRepository
{
    public Account Save(Account account);
    public Account? FindByName(string name);
    public Account Detach(Account account);
}