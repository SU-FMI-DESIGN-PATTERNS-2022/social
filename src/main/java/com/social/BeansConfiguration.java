package com.social;

import com.social.comments.repository.CommentsRepository;
import com.social.comments.service.CommentsService;
import com.social.posts.repository.PostsRepository;
import com.social.posts.service.PostsService;
import com.social.users.repository.SocialUsersRepository;
import com.social.users.service.SocialUsersService;
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
  public CommentsService commentsService(CommentsRepository commentsRepository) {
    return new CommentsService(commentsRepository);
  }


  @Bean
  public ReactionsService reactionsService(ReactionsRepository reactionRepository) {
    return new ReactionsService(reactionRepository);
  }

  @Bean
  public PostsService postsService(PostsRepository postsRepository, CommentsService commentsService) {
    return new PostsService(postsRepository, commentsService);
  }

  @Bean
  public SocialUsersService socialUsersService(SocialUsersRepository socialUsersRepository) {
    return new SocialUsersService(socialUsersRepository);
  }
}
