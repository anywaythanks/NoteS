using Microsoft.AspNetCore.Authorization;
using Microsoft.EntityFrameworkCore.Metadata.Internal;
using NoteS.Models;

namespace NoteS.Attributes;

/**
 * Какая же чушь, боже. https://stackoverflow.com/questions/16710533/passing-static-array-in-attribute
 */
public class KeycloakAuthorizeAttribute : AuthorizeAttribute
{
    public KeycloakAuthorizeAttribute(Policies policy)
    {
        Roles = PolicyTransform(policy);
    }

    private static string PolicyTransform(Policies policy)
    {
        switch (policy)
        {
            case Policies.READ_NOTES: return "read-notes";
            case Policies.SEARCH_OWN_NOTES: return "search-own-notes";
            case Policies.EDIT_OWN_NOTES: return "edit-own-notes";
            case Policies.READ_COMMENTS: return "read-comments";
            case Policies.EDIT_OWN_COMMENTS: return "edit-own-comments";
            case Policies.EDIT_ALL_COMMENTS: return "edit-all-comments";
        }

        throw new ArgumentException("Invalid policy"); //Какой же все таки прекрасный язык, да.
    }
}