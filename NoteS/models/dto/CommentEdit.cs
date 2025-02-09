using NoteS.models.entity;

namespace NoteS.models.dto;

public class CommentCreateRequestDto(string title, SyntaxType type, string content)
    : NoteCreateRequestDto(title, type, content)
{
}

public class CommentCreateResponseDto(
    string path,
    string title,
    NoteTypes type,
    bool isPublic,
    SyntaxType syntaxType,
    string content)
{
    public string Path { get; } = path;
    public string Title { get; } = title;
    public NoteTypes Type { get; } = type;
    public bool IsPublic { get; } = isPublic;
    public SyntaxType SyntaxType { get; } = syntaxType;
    public string Content { get; } = content;
}

public class CommentEditRequestDto(string title, SyntaxType type, string content)
{
    public string Title => title;
    public SyntaxType Type => type;
    public string Content => content;
}

public class CommentEditResponseDto(
    string path,
    string title,
    NoteTypes type,
    bool isPublic,
    SyntaxType syntaxType,
    string content)
{
    public string Path { get; } = path;
    public string Title { get; } = title;
    public NoteTypes Type { get; } = type;
    public bool IsPublic { get; } = isPublic;
    public SyntaxType SyntaxType { get; } = syntaxType;
    public string Content { get; } = content;
}