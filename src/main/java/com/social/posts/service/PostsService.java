package com.social.posts.service;

import com.social.comments.service.CommentsService;
import com.social.posts.model.Post;
import com.social.posts.repository.PostsRepository;
import com.social.users.model.SocialUser;

import java.util.UUID;

public class PostsService {

  private final PostsRepository postsRepository;
  private final CommentsService commentsService;

  public PostsService(PostsRepository postsRepository, CommentsService commentsService) {
    this.postsRepository = postsRepository;
    this.commentsService = commentsService;
  }

  public void createPost(Post post) {
    postsRepository.save(post);
  }

  public void updatePostContent(UUID postId, UUID userId, String content, Post.ContentType contentType) {
    postsRepository.updateContentAndContentTypeByPostIdAndCreatedBy(postId, new SocialUser(userId), content, contentType);
  }

  public void updatePostIsPrivate(UUID postId, UUID userId, Boolean isPrivate) {
    postsRepository.updateIsPrivateByPostIdAndCreatedBy(postId, new SocialUser(userId), isPrivate);
  }

  public void deletePost(UUID postId, UUID userId) {
    postsRepository.deleteByPostIdAndCreatedBy(postId, new SocialUser(userId));
    commentsService.deleteCommentsByPostId(postId);
    //TODO delete all reactions
  }

  public void updatePost(UUID postId, UUID userId, String content, Post.ContentType contentType, Boolean isPrivate) {
    postsRepository.updateContentAndContentTypeAndIsPrivateByPostIdAnAndCreatedBy(postId, new SocialUser(userId), content, contentType, isPrivate);
  }

  public boolean existsById(UUID id) {
    return postsRepository.existsById(id);
  }
}
