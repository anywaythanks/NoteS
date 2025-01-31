namespace NoteS.models;

public class NoteTypes
{
    public int Num { get; }
    public string Name { get; }
    public static NoteTypes NOTE = new NoteTypes(0, "NOTE");
    public static NoteTypes COMMENT = new NoteTypes(1, "COMMENT");
    
    public static NoteTypes NumToType(int num)
    {
        switch (num)
        {
            case 0: return NOTE;
            case 1: return COMMENT;
        }

        throw new ArgumentException();//TODO: другая ошибка
    }

    private NoteTypes(int num, string name)
    {
        Num = num;
        Name = name;
    }

    
}