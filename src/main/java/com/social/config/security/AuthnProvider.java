package com.social.config.security;

import com.social.users.model.SocialUser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AuthnProvider implements AuthenticationProvider {

  private final RestTemplate restTemplate;
  private final Map<String, SocialUser> existingSessions;

  public AuthnProvider() {
    this.restTemplate = new RestTemplate();
    this.existingSessions = new ConcurrentHashMap<>();
    this.existingSessions.put("test", new SocialUser(UUID.randomUUID()));
  }


  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String token = (String) authentication.getCredentials();
    if (token == null) {
      throw new BadCredentialsException("Invalid credentials");
    }
    if (existingSessions.containsKey(token)) {
      return new AuthnToken(existingSessions.get(token), token);
    }
    throw new BadCredentialsException("Invalid credentials");
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.isAssignableFrom(AuthnToken.class);
  }

  private SocialUser delegateAuthentication(String authnHeader) {
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.AUTHORIZATION, authnHeader);
    HttpEntity<String> request = new HttpEntity<>(headers);
    ResponseEntity<SocialUser> response = restTemplate.exchange("authnServerUrl", HttpMethod.POST, request, SocialUser.class);
    if (response.getStatusCode().equals(HttpStatus.OK)) {
      return response.getBody();//TODO discuss what the auth service will return
    }
    return null;
  }
}
