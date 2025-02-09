using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Diagnostics.CodeAnalysis;

namespace NoteS.models.entity;

[Table("custom_types_syntax")]
public class SyntaxType
{
    [Column("id")] [Key] public int Num { get; init; }
    [Column("syntax")] public string Name { get; init; }
    public static readonly SyntaxType Plain = new(0, "PLAINTEXT");
    public static readonly SyntaxType Markdown = new(1, "MARKDOWN");

    public static int? TypeToNum(SyntaxType? syntaxType)
    {
        return syntaxType?.Num;
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

    protected SyntaxType(int num, string name)
    {
        Num = num;
        Name = name;
    }
}