package com.social.comments.api;

import com.social.api.CommentsApi;
import com.social.model.CommentRequestDto;
import com.social.model.CommentResponseDto;
import org.springframework.http.ResponseEntity;

public class CommentsController implements CommentsApi {
  @Override
  public ResponseEntity<Void> deleteCommentsCommentId(String commentId) {
    return null;
  }

  @Override
  public ResponseEntity<CommentResponseDto> putComments(CommentRequestDto commentRequestDto) {
    return null;
  }
}
