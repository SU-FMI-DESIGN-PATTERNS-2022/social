package com.social;

import com.social.comments.service.CommentsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfiguration {

  @Bean
  public CommentsService commentsService() {
    return new CommentsService();
  }
}
