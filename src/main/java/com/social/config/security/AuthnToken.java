package com.social.config.security;

import com.social.users.model.SocialUser;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;

public class AuthnToken extends AbstractAuthenticationToken {
  private SocialUser principal;
  private final String credentials;


  public AuthnToken(String credentials) {
    super(Collections.emptyList());
    this.credentials = credentials;
    setAuthenticated(false);
  }

  public AuthnToken(SocialUser principal, String credentials) {
    super(List.of(new SimpleGrantedAuthority("ROLE_USER")));
    this.principal = principal;
    this.credentials = credentials;
    setAuthenticated(true);
  }

  @Override
  public String getCredentials() {
    return credentials;
  }

  @Override
  public SocialUser getPrincipal() {
    return principal;
  }
}
