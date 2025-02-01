using NoteS.Models;
using NoteS.models.dto.accounts;
using Riok.Mapperly.Abstractions;

namespace NoteS.mappers;

[Mapper(RequiredMappingStrategy = RequiredMappingStrategy.Target,
    ThrowOnMappingNullMismatch = true,
    ThrowOnPropertyMappingNullMismatch = true)]
public partial class AccountMapper
{
    [MapProperty("Id", "Id")]
    public partial AccountFullDto ToFullDto(Account account);

    public partial AccountPartialDto ToPartialDto(Account account);
}