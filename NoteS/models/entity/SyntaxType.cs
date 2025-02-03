namespace NoteS.models;

public class SyntaxType
{
    public int Num { get; }
    public string Name { get; }
    public static readonly SyntaxType Plain = new(0, "PLAINTEXT");
    public static readonly SyntaxType Markdown = new(1, "MARKDOWN");
    public static SyntaxType NumToType(int num)
    {
        switch (num)
        {
            case 0: return Plain;
            case 1: return Markdown;
        }

        throw new ArgumentException();//TODO: другая ошибка
    }

    private SyntaxType(int num, string name)
    {
        Num = num;
        Name = name;
    }
}