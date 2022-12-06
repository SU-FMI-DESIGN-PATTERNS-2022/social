package com.social.comments.api;

import com.social.api.CommentsApi;
import com.social.comments.model.Comment;
import com.social.comments.model.CommentContentType;
import com.social.comments.service.CommentsService;
import com.social.model.CommentPatchDto;
import com.social.model.CommentPostDto;
import com.social.model.ParentType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CommentsController implements CommentsApi {

  private final CommentsService commentsService;

  public CommentsController(CommentsService commentsService) {
    this.commentsService = commentsService;
  }

  @Override
  public ResponseEntity<Void> commentsCommentIdPatch(String commentId, CommentPatchDto commentPatchDto) {
    String userId = "JFDOJ";
    String content = commentPatchDto.getContent();
    CommentContentType contentType = CommentContentType.valueOf(commentPatchDto.getContentType().toString());
    commentsService.updateCommentContent(commentId, userId, content, contentType);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Void> deleteCommentsCommentId(String commentId) {
    String userId = "JFDOJ";
    commentsService.deleteComment(commentId, userId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Void> postComments(CommentPostDto commentPostDto) {
    Comment comment = commentPostDtoToComment(commentPostDto);
    if (!commentsService.doesCommentExist(comment.getParentComment())) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    //TODO: Check if parent exists
    commentsService.createComment(comment);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  private Comment commentPostDtoToComment(CommentPostDto commentPostDto) {
    String userId = "JFDOJ";
    String parentPost = commentPostDto.getParent();
    String content = commentPostDto.getContent();
    CommentContentType contentType = CommentContentType.valueOf(commentPostDto.getContentType().toString());
    String parentComment = commentPostDto.getParentComment();
    ParentType parentType = commentPostDto.getParentType();
    return new Comment(userId, parentPost, content, contentType, parentComment, parentType);
  }
}
