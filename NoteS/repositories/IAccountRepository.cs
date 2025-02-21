using NoteS.models.entity;

namespace NoteS.repositories;

public interface IAccountRepository
{
    public Account Save(Account account);
    public Account? FindByName(AccNameDto name);
    public Account? FindById(AccIdDto id);
    public Account? FindByUuid(AccUuidDto uuid);
}