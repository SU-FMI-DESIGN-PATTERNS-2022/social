package com.social.config.security.prod;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthnFilter extends OncePerRequestFilter {

  private final AuthenticationConverter converter;
  private final AuthenticationManager authenticationManager;

  public AuthnFilter(AuthenticationConverter converter, AuthenticationManager authenticationManager) {
    this.converter = converter;
    this.authenticationManager = authenticationManager;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    Authentication authentication = converter.convert(request);
    Authentication result = authenticationManager.authenticate(authentication);
    SecurityContextHolder.getContext().setAuthentication(result);
    filterChain.doFilter(request, response);
  }
}
