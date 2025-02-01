using LR.model.dto.user;
using NoteS.Models;
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

    public Account ToModel(AccountPartialDto partialDto)
    {
        return new Account(partialDto.Role, partialDto.Name, "") { Amount = partialDto.Amount };
    }

    public Account ToModel(AccountFullDto fullDto)
    {
        return new Account(fullDto.Role, fullDto.Name, "")
            { Id = fullDto.Id, Amount = fullDto.Amount, Password = fullDto.Password };
    }
}