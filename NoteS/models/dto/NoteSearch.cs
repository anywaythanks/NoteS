using NoteS.Models;

namespace NoteS.models.dto;

public class NoteSearchResponseDto(string title)
{
    public string Title => title;
}
public class NoteSearchContentResponseDto(string title, string content, List<TagDto> tags)
{
    public string Title => title;
    public string Content => content;
    public List<TagDto> Tags => tags;
}
public class NoteSearchRequestDto(string title)
{
    public string Title => title;
}

public class NoteSemanticSearchRequestDto(string query)
{
    public string Query => query;
}