package com.social.config.security.prod;

import com.social.config.security.AuthnToken;
import com.social.users.model.SocialUser;
import org.springframework.beans.factory.annotation.Value;
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

public class AuthnProvider implements AuthenticationProvider {

  private final RestTemplate restTemplate;

  @Value("${authentication-server.url}")
  private String authenticationServerUrl;

  public AuthnProvider() {
    this.restTemplate = new RestTemplate();
  }


  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String token = authentication.getCredentials().toString();
    if (token == null) {
      throw new BadCredentialsException("No credentials provided");
    }
    SocialUser user = delegateAuthentication(token);
    return new AuthnToken(user, token);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.isAssignableFrom(AuthnToken.class);
  }

  private SocialUser delegateAuthentication(String token) {
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    HttpEntity<String> request = new HttpEntity<>(headers);
    ResponseEntity<SocialUser> response = restTemplate.exchange(authenticationServerUrl, HttpMethod.POST, request, SocialUser.class);
    if (response.getStatusCode().equals(HttpStatus.OK)) {
      return response.getBody();//TODO discuss what the auth service will return
    }
    throw new BadCredentialsException("Invalid credentials");
  }
}
