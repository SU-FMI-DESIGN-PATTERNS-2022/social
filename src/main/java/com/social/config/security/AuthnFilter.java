package com.social.config.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFilter;

public class AuthnFilter extends AuthenticationFilter {
  public AuthnFilter(AuthenticationManager authenticationManager, AuthenticationConverter authenticationConverter) {
    super(authenticationManager, authenticationConverter);
  }

}
