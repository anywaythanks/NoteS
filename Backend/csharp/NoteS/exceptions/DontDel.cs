namespace NoteS.exceptions;

public class DontDel(string resource)
    : StatusCodeException(StatusCodes.Status409Conflict, "dont_del", "Не удален", $"{resource} не удален.")
{
}