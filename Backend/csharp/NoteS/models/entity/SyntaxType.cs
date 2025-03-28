using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Diagnostics.CodeAnalysis;
using System.Text.Json.Serialization;

namespace NoteS.models.entity;

[Table("custom_types_syntax")]
public class SyntaxType
{
    [Column("id")] [Key] public int Num { get; init; }
    [Column("syntax")] public SyntaxTypeName Name { get; init; }
    public static readonly SyntaxType Plain = new(0, SyntaxTypeName.PLAINTEXT);
    public static readonly SyntaxType Markdown = new(1, SyntaxTypeName.MARKDOWN);

    [JsonConverter(typeof(JsonStringEnumConverter))]
    public enum SyntaxTypeName
    {
        PLAINTEXT,
        MARKDOWN
    }

    public static int? TypeToNum(SyntaxType? syntaxType)
    {
        return syntaxType?.Num;
    }
    [return: NotNullIfNotNull(nameof(syntaxType))]
    public static SyntaxType? NameToType(SyntaxTypeName? syntaxType)
    {
        if (syntaxType == null) return null;
        switch (syntaxType)
        {
            case SyntaxTypeName.PLAINTEXT: return Plain;
            case SyntaxTypeName.MARKDOWN: return Markdown;
        }

        throw new ArgumentException();
    }

    [return: NotNullIfNotNull(nameof(num))]
    public static SyntaxType? NumToType(int? num)
    {
        if (num == null) return null;
        switch (num)
        {
            case 0: return Plain;
            case 1: return Markdown;
        }

        throw new ArgumentException(); //TODO: другая ошибка
    }

    protected SyntaxType()
    {
    }

    protected SyntaxType(int num, SyntaxTypeName name)
    {
        Num = num;
        Name = name;
    }
}