using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Filters;
using NoteS.Models;

namespace NoteS.Attributes;

/**
 * Какая же чушь, боже. <a href="https://stackoverflow.com/questions/16710533/passing-static-array-in-attribute"/>
 */
public class KeycloakAuthorizeAttribute(params Policies[] policies) : AuthorizeAttribute, IAuthorizationFilter
{
    private static string PolicyTransform(Policies policy)
    {
        return policy switch
        {
            Policies.READ_NOTES => "read-notes", //TODO: добавить в кейклок
            Policies.READ_ALL_NOTES => "read-all-notes",
            Policies.SEARCH_OWN_NOTES => "search-own-notes",
            Policies.EDIT_OWN_NOTES => "edit-own-notes",
            Policies.EDIT_ALL_NOTES => "edit-all-notes",
            Policies.READ_COMMENTS => "read-comments",
            Policies.EDIT_OWN_COMMENTS => "edit-own-comments",
            Policies.EDIT_ALL_COMMENTS => "edit-all-comments",
            Policies.SET_ALL_PUBLIC_STATUS_NOTES => "set-all-public-status-notes",
            Policies.SET_OWN_PUBLIC_STATUS_NOTES => "set-own-public-status-notes",
            Policies.CREATE_COMMENTS => "create-comments",
            Policies.CREATE_NOTES => "create-notes",
            Policies.DELETE_NOTES => "delete-notes",
            Policies.DELETE_COMMENTS => "delete-comments",
            _ => throw new ArgumentException("Invalid policy") //Какой же все таки прекрасный язык, да.
        };
    }

    public void OnAuthorization(AuthorizationFilterContext context)
    {
        var user = context.HttpContext.User;
        
        if (user.Identity is not { IsAuthenticated: true })
        {
            context.Result = new UnauthorizedResult();
            return;
        }

        if (policies.All(policy => user.IsInRole(PolicyTransform(policy)))) return;
        context.Result = new ForbidResult();
    }
}