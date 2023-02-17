package com.social.comments.api;

import com.social.api.CommentsApi;
import com.social.comments.model.Comment;
import com.social.comments.model.CommentContentType;
import com.social.comments.service.CommentsService;
import com.social.model.CommentPatchDto;
import com.social.model.CommentPostDto;
import com.social.users.model.SocialUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/")
public class CommentsController implements CommentsApi {

  private final CommentsService commentsService;

  public CommentsController(CommentsService commentsService) {
    this.commentsService = commentsService;
  }

  @Override
  public ResponseEntity<Void> patchComment(UUID commentId, CommentPatchDto commentPatchDto) {
    SocialUser user = (SocialUser) SecurityContextHolder.getContext().getAuthentication();
    byte[] content = commentPatchDto.getContent();
    CommentContentType contentType = CommentContentType.valueOf(commentPatchDto.getContentType().toString().toUpperCase());
    commentsService.updateCommentContent(commentId, user.getId(), content, contentType);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Void> deleteComment(UUID commentId) {
    SocialUser user = (SocialUser) SecurityContextHolder.getContext().getAuthentication();
    commentsService.deleteComment(commentId, user.getId());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Void> postComments(CommentPostDto commentPostDto) {
    Comment comment = commentPostDtoToComment(commentPostDto);
    commentsService.createComment(comment, commentPostDto.getParentType());
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  private Comment commentPostDtoToComment(CommentPostDto commentPostDto) {
    SocialUser user = (SocialUser) SecurityContextHolder.getContext().getAuthentication();
    return Comment.builder()
        .commentId(UUID.randomUUID())
        .userId(user.getId())
        .pathToComment(commentPostDto.getParentId())
        .content(commentPostDto.getContent())
        .contentType(CommentContentType.valueOf(commentPostDto.getContentType().toString().toUpperCase()))
        .build();
  }
}
