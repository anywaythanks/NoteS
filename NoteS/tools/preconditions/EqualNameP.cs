using System.Security.Principal;
using NoteS.Models;

namespace NoteS.tools.preconditions;

public class EqualNameP : GeneralPrecondition
{
    public bool check(IIdentity? identity, string accountName)
    {
        if (identity == null) return false;
        return accountName != identity.Name;
    }
}