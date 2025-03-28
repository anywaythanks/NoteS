using Microsoft.AspNetCore.Mvc;
using NoteS.exceptions;
using NoteS.models.entity;
using NoteS.services;

namespace NoteS.tools;

public abstract class GeneralPreconditionController(AccountRegisterService service) : Controller
{
    protected void Check(AccNameDto accNameDto)
    {
        if (User.Identity?.Name != accNameDto.Name) throw new Forbidden("аккаунту");
        var value = User.FindFirst("sid")?.Value ?? throw new NotFound("пользователь");
        service.Register(accNameDto, new(value));
    }
}