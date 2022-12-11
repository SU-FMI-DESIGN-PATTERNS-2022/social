package com.social.comments.repository;

import com.social.comments.model.Comment;
import com.social.comments.model.CommentContentType;
import com.social.model.ParentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, String> {

  void deleteAllByParentAndParentType(UUID parent, ParentType parentType);
  void deleteByIdAndUserId(String id, String userId);

  @Modifying
  @Query("UPDATE Comment c SET c.content = ?3, c.contentType = ?4 WHERE c.id=?1 AND c.userId =?2")
  void updateContentAndContentTypeByIdAndUserId(String id, String userId, String content, CommentContentType contentType);
}