package com.notes.models.auth;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserPrincipal {
   private final String uuid;
   private final String username;
   private final Collection<? extends GrantedAuthority> grantedAuthorityList;

   public UserPrincipal(String uuid, String username, Collection<? extends GrantedAuthority> grantedAuthorityList) {
      this.uuid = uuid;
      this.username = username;
      this.grantedAuthorityList = grantedAuthorityList;
   }

   public String getUuid() {
      return uuid;
   }

   public String getUsername() {
      return username;
   }

   public Collection<? extends GrantedAuthority> getGrantedAuthorityList() {
      return grantedAuthorityList;
   }
}
