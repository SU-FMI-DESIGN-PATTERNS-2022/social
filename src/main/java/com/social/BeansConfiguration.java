package com.social;

import com.social.comments.repository.CommentsRepository;
import com.social.comments.service.CommentsService;
import com.social.posts.repository.PostsRepository;
import com.social.posts.service.PostsService;
import com.social.reactions.repository.ReactionsRepository;
import com.social.reactions.service.ReactionsService;
import com.social.users.repository.SocialUsersRepository;
import com.social.users.service.SocialUsersService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfiguration {


  @Bean
  public CommentsService commentsService(CommentsRepository commentsRepository) {
    return new CommentsService(commentsRepository);
  }


  @Bean
  public ReactionsService reactionsService(ReactionsRepository reactionRepository, PostsRepository postsRepository, CommentsRepository commentsRepository) {
    return new ReactionsService(reactionRepository, postsRepository, commentsRepository);
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
