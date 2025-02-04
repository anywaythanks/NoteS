using NoteS.Models;

namespace NoteS.models.dto;

public class NoteSearchResponseDto(string title)
{
    public string Title => title;
}
public class NoteSearchContentResponseDto(string title, string content, List<TagResponseDto> tags)
{
    public string Title => title;
    public string Content => content;
    public List<TagResponseDto> Tags => tags;
}
public class NoteSearchRequestDto(string title)
{
    public string Title => title;
}

public class NoteSearchTagsRequestDto(string tag)
{
    public string Tag => tag;
}
public class NoteSemanticSearchRequestDto(string query)
{
    public string Query => query;
}