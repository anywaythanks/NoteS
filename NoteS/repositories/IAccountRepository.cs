using NoteS.models.entity;

namespace NoteS.repositories;

public interface IAccountRepository
{
    public Account Save(Account account);
    public Account? FindByName(Field<IAccName, string> name);
    public Account? FindByUuid(Field<IAccUid, string> uuid);
}