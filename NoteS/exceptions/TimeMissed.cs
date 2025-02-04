namespace NoteS.exceptions;

public class TimeMissed(string op)
    : StatusCodeException(StatusCodes.Status403Forbidden, "time_missed", $"Операция {op} более недоступна.")
{
}