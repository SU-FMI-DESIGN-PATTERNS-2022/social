package com.social;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class SocialApplication {

  public static void main(String[] args) {
    SpringApplication.run(SocialApplication.class, args);
  }

}
