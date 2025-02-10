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

    [MapProperty("AccountName", "Val")]
    public partial Field<IAccName, string> Of(AccName account);

    [MapProperty("Uuid", "Val")]
    public partial Field<IAccUuid, string> ToUuid(Account account);

    [MapProperty("Owner", "Val")]
    public partial Field<IAccId, int> Of(Note note);
}