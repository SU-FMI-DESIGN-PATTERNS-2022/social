package com.social.comments.service;

import com.social.comments.model.Comment;
import com.social.comments.model.CommentContentType;
import com.social.comments.repository.CommentsRepository;
import com.social.model.ParentType;

import java.util.UUID;

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

  public void deleteCommentContent(String commentId, String userId) {
    commentsRepository.updateContentAndContentTypeByIdAndUserId(commentId, userId, "", CommentContentType.DELETED);
  }

  public void deleteCommentsByPostId(UUID postId) {
    commentsRepository.deleteAllByParentAndParentType(postId, ParentType.POST);
    //TODO delete all reactions
  }

  public void updateCommentContent(String commentId, String userId, String content, CommentContentType contentType) {
    commentsRepository.updateContentAndContentTypeByIdAndUserId(commentId, userId, content, contentType);
  }
}
