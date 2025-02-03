using System.Security.Principal;

namespace NoteS.tools.preconditions;

public interface IGeneralPrecondition
{
    public bool Check(IIdentity? identity, string uuid);
}