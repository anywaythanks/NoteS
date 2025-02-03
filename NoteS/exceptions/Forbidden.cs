namespace NoteS.exceptions;

public class Forbidden() : StatusCodeException(StatusCodes.Status403Forbidden)
{
}