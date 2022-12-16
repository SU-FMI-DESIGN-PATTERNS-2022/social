package com.social.config.security.dev;

import com.social.users.model.SocialUser;
import com.social.users.repository.SocialUsersRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.UUID;

@Profile("dev")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class DevSecurityConfig {

  private final SocialUsersRepository usersRepository;

  public DevSecurityConfig(SocialUsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }

  public static final SocialUser testUser = new SocialUser(
      UUID.fromString("19fa4141-6d98-448f-b448-ea4853d55462"),
      "firstName",
      "lastName",
      Collections.emptyList()
  );

  @PostConstruct
  public void init() {

    usersRepository.save(testUser);
  }


  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
    return authenticationManagerBuilder.build();
  }

  @Bean
  public DefaultUserFilter defaultUserFilter() {
    return new DefaultUserFilter();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, DefaultUserFilter defaultUserFilter) throws Exception {
    http.csrf().disable()
        .cors()
        .and()
        .exceptionHandling();

    http.authorizeRequests()
        .anyRequest()
        .authenticated();

    http.addFilterBefore(defaultUserFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
