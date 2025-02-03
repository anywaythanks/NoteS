namespace NoteS.exceptions;

public class NotEnoughAmount() : StatusCodeException(StatusCodes.Status409Conflict)
{
}