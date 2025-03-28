package com.notes.models.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;

public class AuthorizationTokenUser extends AbstractAuthenticationToken {
   private final UserPrincipal principal;
   private final Jwt jwt;

   public AuthorizationTokenUser(UserPrincipal principal, Jwt jwt) {
      super(principal.getGrantedAuthorityList());
      this.principal = principal;
      this.jwt = jwt;
   }

   @Override
   public Jwt getCredentials() {
      return jwt;
   }

   @Override
   public UserPrincipal getPrincipal() {
      return principal;
   }

   @Override
   public boolean isAuthenticated() {
      return true;
   }
}
