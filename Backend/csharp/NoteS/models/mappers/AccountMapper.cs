using NoteS.models.dto;
using NoteS.models.entity;
using Riok.Mapperly.Abstractions;

namespace NoteS.models.mappers;

[Mapper(RequiredMappingStrategy = RequiredMappingStrategy.Target,
    EnabledConversions = MappingConversionType.Constructor)]
public partial class AccountMapper
{
    public partial AccIdDto ToIdDto(Account account);
    public partial AccUuidDto ToUuidDto(Account account);
    public partial AccNameDto ToNameDto(Account account);

    [MapProperty("AccountName", "Name")]
    public partial AccNameDto ToNameDto(AccName account);
}