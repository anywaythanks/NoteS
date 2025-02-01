namespace NoteS.models;

public class NoteTypes
{
    public int Num { get; }
    public string Name { get; }
    public static readonly NoteTypes Note = new(0, "NOTE");
    public static readonly NoteTypes Comment = new(1, "COMMENT");
    
    public static NoteTypes NumToType(int num)
    {
        switch (num)
        {
            case 0: return Note;
            case 1: return Comment;
        }

        throw new ArgumentException();//TODO: другая ошибка
    }

    private NoteTypes(int num, string name)
    {
        Num = num;
        Name = name;
    }
}