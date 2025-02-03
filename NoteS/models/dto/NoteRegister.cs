namespace NoteS.models.dto;

public class NoteRegisterRequestDto(string title)
{
    public string Title => title;
}

public class NoteRegisterResponseDto(string title)
{
    public string Title => title;
}