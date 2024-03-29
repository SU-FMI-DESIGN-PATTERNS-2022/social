package com.social.config.security.prod;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Profile("prod")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

  @Bean
  public AuthenticationConverter authenticationConverter() {
    return new AuthnConverter();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    return new AuthnProvider();
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http, AuthenticationProvider authnProvider) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
    authenticationManagerBuilder.authenticationProvider(authnProvider);
    return authenticationManagerBuilder.build();
  }

  @Bean
  public AuthnFilter authnFilter(AuthenticationManager authenticationManager, AuthenticationConverter authenticationConverter) {
    return new AuthnFilter(authenticationConverter, authenticationManager);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, AuthnFilter authnFilter) throws Exception {
    http.csrf().disable()
        .cors()
        .and()
        .exceptionHandling();

    http.authorizeRequests()
        .anyRequest()
        .authenticated();

    http.addFilterBefore(authnFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
