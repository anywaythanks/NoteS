namespace NoteS.tools;

public class CalculateUtil
{
    public static long TotalPages(long total, long limit)
    {
        return total / limit + Math.Sign(total % limit);
    }

    public static int TotalPages(int total, int limit)
    {
        return total / limit + Math.Sign(total % limit);
    }

    public static long ToOffset(long page, long limit)
    {
        return Math.Max((page - 1) * limit, 0);
    }

    public static int ToOffset(int page, int limit)
    {
        return Math.Max((page - 1) * limit, 0);
    }

    public static int CurrentPage(int page, int limit, int total)
    {
        return page;
    }

    public static long CurrentPage(long page, long limit, long total)
    {
        return page;
    }
}