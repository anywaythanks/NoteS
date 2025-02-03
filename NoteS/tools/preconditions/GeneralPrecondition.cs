using System.Security.Claims;
using System.Security.Principal;
using NoteS.Models;

namespace NoteS.tools.preconditions;

public interface GeneralPrecondition
{
    public bool check(IIdentity? identity, string accountName);

    
}