using System.Security.Principal;
using NoteS.models.entity;

namespace NoteS.tools.preconditions;

public interface IGeneralPrecondition
{
    public bool Check(IIdentity? identity, Field<IAccUid, string> uuid);
}