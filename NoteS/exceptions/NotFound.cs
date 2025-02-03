namespace NoteS.exceptions;

public class NotFound() : StatusCodeException(StatusCodes.Status404NotFound)
{
}