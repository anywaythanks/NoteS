using Microsoft.AspNetCore.Mvc;

namespace NoteS.tools.preconditions;

public class GeneralPreconditionController(GeneralPrecondition generalPrecondition) : Controller
{
    public IResult Execute<T>(string accountName, T value)
    {
        return generalPrecondition.check(User.Identity, accountName) ? Results.Unauthorized() : Results.Json(value);
    }
}