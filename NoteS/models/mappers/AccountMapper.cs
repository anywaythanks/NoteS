using NoteS.models.dto;
using NoteS.models.entity;
using Riok.Mapperly.Abstractions;

namespace NoteS.models.mappers;

[Mapper(RequiredMappingStrategy = RequiredMappingStrategy.Target)]
public partial class AccountMapper
{
    [MapProperty("Id", "Val")]
    public partial Field<IAccId, int> ToId(Account account);

    [MapProperty("Name", "Val")]
    public partial Field<IAccName, string> ToName(Account account);

    [MapProperty("Name", "Val")]
    public partial Field<IAccName, string> Of(AccountName account);

    [MapProperty("Uid", "Val")]
    public partial Field<IAccUid, string> ToUid(Account account);
}