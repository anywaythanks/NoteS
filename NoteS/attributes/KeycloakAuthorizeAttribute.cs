using Microsoft.AspNetCore.Authorization;
using Microsoft.EntityFrameworkCore.Metadata.Internal;
using NoteS.Models;

namespace NoteS.Attributes;

/**
 * Какая же чушь, боже. https://stackoverflow.com/questions/16710533/passing-static-array-in-attribute
 */
public class KeycloakAuthorizeAttribute : AuthorizeAttribute
{
    public KeycloakAuthorizeAttribute(params Policies[] policy)
    {
        Roles = string.Join(",", policy.Select(PolicyTransform));
    }

    private static string PolicyTransform(Policies policy)
    {
        return policy switch
        {
            Policies.READ_NOTES => "read-notes",
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
            Policies.DELETE_COMMENTS  => "delete-comments",
            _ => throw new ArgumentException("Invalid policy") //Какой же все таки прекрасный язык, да.
        };
    }
}