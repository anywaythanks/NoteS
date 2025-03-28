using NoteS.models.mappers;

namespace NoteS.models.entity;

using static MappersInstances;

public partial class Account
{
    public static implicit operator AccIdDto(Account account) => am.ToIdDto(account);
    public static implicit operator AccNameDto(Account account) => am.ToNameDto(account);
    public static implicit operator AccUuidDto(Account account) => am.ToUuidDto(account);
}

public readonly struct AccIdDto(int id)
{
    public int Id { get; init; } = id;
}

public readonly struct AccNameDto(string name)
{
    public string Name { get; init; } = name;
}

public readonly struct AccUuidDto(string uuid)
{
    public string Uuid { get; init; } = uuid;
}