package com.social.comments.service;

import com.social.comments.model.Comment;
import com.social.comments.model.CommentContentType;
import com.social.comments.repository.CommentsRepository;

public class CommentsService {

  private final CommentsRepository commentsRepository;

  public CommentsService(CommentsRepository commentsRepository) {
    this.commentsRepository = commentsRepository;
  }

  public boolean doesCommentExist(String commentId) {
    return commentsRepository.existsById(commentId);
  }

  public void createComment(Comment comment) {
    commentsRepository.save(comment);
  }

  public void deleteComment(String commentId, String userId) {
    commentsRepository.deleteByIdAndUserId(commentId, userId);
  }

  public void updateCommentContent(String commentId, String userId, String content, CommentContentType contentType) {
    commentsRepository.updateContentAndContentTypeByIdAndUserId(commentId, userId, content, contentType);
  }
}
