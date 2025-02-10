namespace NoteS.models.dto;

public class TagResponseDto
{
    public string Name { get; set; }
}

public class CreateTagRequestDto
{
    public string Name { get; set; }
}

public class DeleteTagRequestDto
{
    public string Name { get; set; }
}