namespace NoteS.exceptions;

public class AlreadyExists(string resource)
    : StatusCodeException(StatusCodes.Status409Conflict, "already_exists", "Уже существует", $"{resource} уже существует.")
{
}