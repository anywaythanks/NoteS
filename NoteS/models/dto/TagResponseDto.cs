namespace NoteS.models.dto;

public class TagResponseDto(string name)
{
    public string Name => name;
}

public class CreateTagRequestDto(string name)
{
    public string Name => name;
}
public class DeleteTagRequestDto(string name)
{
    public string Name => name;
}