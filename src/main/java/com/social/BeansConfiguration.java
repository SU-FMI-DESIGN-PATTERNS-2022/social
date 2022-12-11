package com.social;

import com.social.comments.service.CommentsService;
import com.social.reactions.model.Reaction;
import com.social.reactions.model.ReactionType;
import com.social.reactions.repository.ReactionsRepository;
import com.social.reactions.service.ReactionsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Configuration
public class BeansConfiguration {


  @Bean
  public CommentsService commentsService() {
    return new CommentsService();
  }


  @Bean
  public ReactionsService reactionsService(ReactionsRepository reactionRepository) {
    return new ReactionsService(reactionRepository);
  }
}
