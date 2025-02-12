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

    public static long ToOffset(long page, long limit, long total)
    {
        return Math.Clamp((page - 1) * limit, 0, Math.Max(total - limit, 0));
    }

    public static int ToOffset(int page, int limit, int total)
    {
        return Math.Clamp((page - 1) * limit, 0, Math.Max(total - limit, 0));
    }

    public static int CurrentPage(int page, int limit, int total)
    {
        var totalPages = TotalPages(total, limit);
        return totalPages == 0 ? 0 : Math.Clamp(page, 1, totalPages);
    }

    public static long CurrentPage(long page, long limit, long total)
    {
        var totalPages = TotalPages(total, limit);
        return totalPages == 0 ? 0 : Math.Clamp(page, 1, totalPages);
    }
}