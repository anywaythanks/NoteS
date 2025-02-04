using Microsoft.EntityFrameworkCore;
using NoteS.Models;
using NoteS.models.dto;

namespace NoteS.repositories;

public sealed class NoteRepositoryDb(DbContextOptions<NoteRepositoryDb> options)
    : DbContext(options), INoteRepository
{
    public Note Save(Note note)
    {
        throw new NotImplementedException();
    }

    public Note SaveContent(Note note)
    {
        throw new NotImplementedException();
    }

    public Note? FindByPath(string path)
    {
        throw new NotImplementedException();
    }

    public Note LoadContent(Note note)
    {
        throw new NotImplementedException();
    }

    public Note LoadTags(Note note)
    {
        throw new NotImplementedException();
    }

    public List<Note> FindByTitle(string title, Account owner)
    {
        throw new NotImplementedException();
    }

    public List<Note> FindByTag(string title, Account owner)
    {
        throw new NotImplementedException();
    }

    public List<Note> FindByOwner(Account owner)
    {
        throw new NotImplementedException();
    }

    public void SaveContent(string content)
    {
        throw new NotImplementedException();
    }

    public List<Note> SemanticFind(string find, Account owner)
    {
        throw new NotImplementedException();
    }

    public Note CreateInElastic(NoteCreateRequestDto requestDto, Account owner)
    {
        throw new NotImplementedException();
    }
}