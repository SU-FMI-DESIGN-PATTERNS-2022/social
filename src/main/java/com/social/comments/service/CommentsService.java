package com.social.comments.service;

import com.social.comments.model.Comment;
import com.social.comments.model.CommentContentType;
import com.social.comments.repository.CommentsRepository;
import com.social.model.ParentType;
import com.social.posts.model.Post;
import com.social.posts.repository.PostsRepository;
import com.social.reactions.repository.ReactionsRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CommentsService {

  private static final String COMMENT_DOES_NOT_EXIST = "Comment does not exist";
  private static final String PARENT_DOES_NOT_EXIST = "Parent does not exist";

  private final CommentsRepository commentsRepository;
  private final PostsRepository postsRepository;
  private final ReactionsRepository reactionsRepository;


  public CommentsService(CommentsRepository commentsRepository, PostsRepository postsRepository, ReactionsRepository reactionsRepository) {
    this.commentsRepository = commentsRepository;
    this.postsRepository = postsRepository;
    this.reactionsRepository = reactionsRepository;
  }

  public void createComment(Comment comment, ParentType parentType) {
    comment.setPathToComment(getPathToParent(comment.getPathToComment(), parentType));
    commentsRepository.save(comment);
  }

  private String getPathToParent(String parentId, ParentType parentType) {
    switch (parentType) {
      case POST -> {
        Optional<Post> post = postsRepository.findById(UUID.fromString(parentId));
        if (post.isEmpty()) {
          throw new IllegalArgumentException(PARENT_DOES_NOT_EXIST);
        } else {
          return post.get().getPostId().toString();
        }
      }
      case COMMENT -> {
        Optional<Comment> comment = commentsRepository.findById(UUID.fromString(parentId));
        if (comment.isEmpty()) {
          throw new IllegalArgumentException(PARENT_DOES_NOT_EXIST);
        } else {
          return comment.get().getPathToComment() + "/" + parentId;
        }
      }
      case NEWS -> {
        return parentId;
      }
    }
    throw new IllegalArgumentException("Invalid parent");
  }

  public void deleteComment(UUID commentId, UUID userId) {
    findCommentByIdIfItBelongsToUser(commentId, userId);
    if (commentsRepository.countCommentChildren(commentId.toString()) == 0) {
      commentsRepository.deleteById(commentId);
    } else {
      commentsRepository.updateContentByCommentId("", commentId);
    }
  }

  public void deleteCommentsByPostId(UUID postId) {
    List<Comment> comments = commentsRepository.findAllByOriginalId(postId.toString());
    deleteAllReactionsByParentIds(comments.stream().map(Comment::getCommentId).map(UUID::toString).toList());
    commentsRepository.deleteAllByIdInBatch(comments.stream().map(Comment::getCommentId).toList());
  }

  public void deleteAllReactionsByParentIds(List<String> parentIds) {
    reactionsRepository.deleteAllByParentIdIn(parentIds);
  }

  public void updateCommentContent(UUID commentId, UUID userId, byte[] content, CommentContentType contentType) {
    Comment comment = findCommentByIdIfItBelongsToUser(commentId, userId);
    comment.setContent(content);
    comment.setContentType(contentType);
    commentsRepository.save(comment);
  }

  private Comment findCommentByIdIfItBelongsToUser(UUID commentId, UUID userId) {
    Optional<Comment> optionalComment = commentsRepository.findById(commentId);
    if (optionalComment.isEmpty()) {
      throw new IllegalArgumentException(COMMENT_DOES_NOT_EXIST);
    }
    Comment comment = optionalComment.get();
    if (!comment.getUserId().equals(userId)) {
      throw new IllegalCallerException("Can not modify other people's comments");
    }
    return comment;
  }
}
