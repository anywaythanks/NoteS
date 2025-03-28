namespace NoteS.exceptions;

public class TimeMissed(string op)
    : StatusCodeException(StatusCodes.Status403Forbidden, "time_missed", "Доступ запрещен",
        $"Операция {op} более недоступна.")
{
}