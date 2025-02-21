namespace NoteS.exceptions;

public class NoteTypeException(string type)
    : StatusCodeException(StatusCodes.Status409Conflict, "type_exception", "Неправильный тип", $"Тип {type} не совпадает.")
{
}