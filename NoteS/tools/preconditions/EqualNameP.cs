using System.Security.Principal;
using NoteS.exceptions;
using NoteS.models.entity;
using NoteS.services;

namespace NoteS.tools.preconditions;

public readonly struct EqualNameP(AccountRegisterService service, Field<IAccName, string> name) : IGeneralPrecondition
{
    private Field<IAccName, string> Name { get; } = name;

    public bool Check(IIdentity? identity, Field<IAccUid, string> uuid)
    {
        if (identity?.Name != Name.Val) throw new Forbidden("аккаунту");
        service.Register(Name, uuid);
        return true;
    }
}