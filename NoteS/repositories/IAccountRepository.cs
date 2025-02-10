using NoteS.models.entity;

namespace NoteS.repositories;

public interface IAccountRepository
{
    public Account Save(Account account);
    public Account? FindByName(Field<IAccName, string> name);
    public Account? FindById(Field<IAccId, int> id);
    public Account? FindByUuid(Field<IAccUuid, string> uuid);
}