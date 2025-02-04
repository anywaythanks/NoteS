namespace NoteS.Models;

public enum Policies
{
    /// Право читать заметки
    READ_NOTES,

    /// Право читать все заметки
    READ_ALL_NOTES,

    /// Право пользоваться поиском по собственным заметкам
    SEARCH_OWN_NOTES,

    /// Право изменять собственные заметки
    EDIT_OWN_NOTES,

    /// Право изменять все заметки
    EDIT_ALL_NOTES,

    /// Право читать комментарии
    READ_COMMENTS,

    /// Право изменять собственные комментарии
    EDIT_OWN_COMMENTS,

    /// Право изменять все комментарии
    EDIT_ALL_COMMENTS,

    /// Право делать собственные заметки публичными/непубличными
    SET_OWN_PUBLIC_STATUS_NOTES,

    /// Право делать все заметки публичными/непубличными
    SET_ALL_PUBLIC_STATUS_NOTES,

    /// Право комментировать заметки
    CREATE_COMMENTS,

    /// Право создавать заметки
    CREATE_NOTES,

    /// Право удалять заметки
    DELETE_NOTES,

    /// Право удалять комментарии
    DELETE_COMMENTS,
}