namespace NoteS.models.entity;

public readonly struct Field<T, TValue>(TValue value) where T : ITypeMarker<TValue>
{
    public TValue Val { get; init; } = value;
}

public interface ITypeMarker<T>
{
}