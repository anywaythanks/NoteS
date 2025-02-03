namespace NoteS.exceptions;

//404
public class NotEnoughProducts() : StatusCodeException(StatusCodes.Status409Conflict)
{
}