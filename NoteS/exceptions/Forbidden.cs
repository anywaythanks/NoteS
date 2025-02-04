namespace NoteS.exceptions;

public class Forbidden(string resource)
    : StatusCodeException(StatusCodes.Status403Forbidden, "forbid", $"Доступ к {resource} запрещен.")
{
}