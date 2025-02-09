using NoteS.models.entity;

namespace NoteS.models.dto;

public class NoteEditPublicRequestDto(bool isPublic)
{
    public bool IsPublic => isPublic;
}

public class NoteEditPublicResponseDto(string title, bool isPublic)
{
    public string Title => title;
    public bool IsPublic => isPublic;
}

public class NoteEditContentRequestDto(string content)
{
    public string Content => content;
}

public class NoteEditContentResponseDto(string title, string content)
{
    public string Title => title;
    public string Content => content;
}

public class NoteCreateRequestDto(string title, SyntaxType type, string content)
{
    public string Title => title;
    public SyntaxType Type => type;
    public string Content => content;
}

public class NoteCreateResponseDto(string path, string title, NoteTypes type, bool isPublic, SyntaxType syntaxType)
{
    public string Path { get; } = path;
    public string Title { get; } = title;
    public NoteTypes Type { get; } = type;
    public bool IsPublic { get; } = isPublic;
    public SyntaxType SyntaxType { get; } = syntaxType;
}

public class SemanticSearchQuery(string query)
{
    public required string Query { get; init; } = query;
}

