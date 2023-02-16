package com.social.comments.repository;

import com.social.comments.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, UUID> {

  @Query("SELECT c FROM Comment c WHERE c.pathToComment LIKE CONCAT(:originalId,'%')")
  List<Comment> findAllByOriginalId(@Param("originalId") String originalId);

  @Query("SELECT COUNT(c) FROM Comment c where c.pathToComment LIKE CONCAT('%', :originalId, '%')")
  int countCommentChildren(String commentId);

  @Modifying
  @Query("UPDATE Comment c SET c.content = :content WHERE c.commentId = :comment_id")
  void updateContentByCommentId(@Param("content") String content, @Param("comment_id") UUID commentId);
}
