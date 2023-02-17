package com.social.posts.repository;

import com.social.posts.model.Post;
import com.social.users.model.SocialUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostsRepository extends JpaRepository<Post, UUID> {

  @Modifying
  @Query("UPDATE Post p set p.content = ?3, p.contentType = ?4, p.isPrivate = ?5 WHERE p.postId = ?1 AND p.createdBy = ?2")
  void updateContentAndContentTypeAndIsPrivateByPostIdAnAndCreatedBy(UUID id, SocialUser socialUser, String content, Post.ContentType contentType, Boolean isPrivate);

  @Modifying
  @Query("UPDATE Post p set p.content = ?3, p.contentType = ?4 WHERE p.postId = ?1 AND p.createdBy = ?2")
  void updateContentAndContentTypeByPostIdAndCreatedBy(UUID id, SocialUser socialUser, String content, Post.ContentType contentType);

  @Modifying
  @Query("UPDATE Post p set p.isPrivate = ?3 WHERE p.postId = ?1 AND p.createdBy = ?2")
  void updateIsPrivateByPostIdAndCreatedBy(UUID id, SocialUser socialUser, Boolean isPrivate);

  void deleteByPostIdAndCreatedBy(UUID id, SocialUser socialUser);

  Page<Post> findAllByCreatedBy(SocialUser createdBy, Pageable pageable);

  Page<Post> findAllByCreatedByIn(List<SocialUser> createdBy, Pageable pageable);

}
