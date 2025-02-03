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
        switch (policy)
        {
            case Policies.READ_NOTES: return "read-notes";
            case Policies.READ_All_NOTES: return "read-all-notes";
            case Policies.SEARCH_OWN_NOTES: return "search-own-notes";
            case Policies.EDIT_OWN_NOTES: return "edit-own-notes";
            case Policies.EDIT_ALL_NOTES: return "edit-all-notes";
            case Policies.READ_COMMENTS: return "read-comments";
            case Policies.EDIT_OWN_COMMENTS: return "edit-own-comments";
            case Policies.EDIT_ALL_COMMENTS: return "edit-all-comments";
            case Policies.SET_ALL_PUBLIC_STATUS_NOTES: return "set-all-public-status-notes";
            case Policies.SET_OWN_PUBLIC_STATUS_NOTES: return "set-own-public-status-notes";
        }

        throw new ArgumentException("Invalid policy"); //Какой же все таки прекрасный язык, да.
    }
}