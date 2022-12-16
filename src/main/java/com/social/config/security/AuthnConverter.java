package com.social.config.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

import javax.servlet.http.HttpServletRequest;

public class AuthnConverter implements AuthenticationConverter {
  @Override
  public Authentication convert(HttpServletRequest request) {
    String authnHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authnHeader != null && authnHeader.startsWith("Bearer")) {
      String token = authnHeader.substring(7);
      return new AuthnToken(token);
    }
    return null;
  }
}
