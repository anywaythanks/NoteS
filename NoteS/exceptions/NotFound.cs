namespace NoteS.exceptions;

public class NotFound(string resource)
    : StatusCodeException(StatusCodes.Status404NotFound, "not_found", $"{resource} не найден")
{
}