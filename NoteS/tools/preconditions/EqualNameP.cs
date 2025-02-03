using System.Security.Principal;
using NoteS.exceptions;
using NoteS.services;

namespace NoteS.tools.preconditions;

public readonly struct EqualNameP(AccountRegisterService service, string accountName) : IGeneralPrecondition
{
    private string AccountName { get; } = accountName;

    public bool Check(IIdentity? identity, string uuid)
    {
        if (identity?.Name != AccountName) throw new Forbidden();
        service.Register(AccountName, uuid);
        return true;
    }
}