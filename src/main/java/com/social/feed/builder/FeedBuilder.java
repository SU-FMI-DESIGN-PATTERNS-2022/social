package com.social.feed.builder;

import com.social.comments.model.Comment;
import com.social.comments.repository.CommentsRepository;
import com.social.model.CommentResponseDto;
import com.social.model.FeedDto;
import com.social.model.ParentType;
import com.social.model.PostContentTypeDto;
import com.social.model.PostResponseDto;
import com.social.model.ReactionDto;
import com.social.model.ReactionResponseDto;
import com.social.model.ReactionTypeDto;
import com.social.posts.model.Post;
import com.social.posts.repository.PostsRepository;
import com.social.reactions.model.Reaction;
import com.social.reactions.repository.ReactionsRepository;
import com.social.users.model.Relation;
import com.social.users.model.SocialUser;
import com.social.users.repository.SocialUsersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class FeedBuilder {

  private final int PAGE_SIZE = 100;

  private final PostsRepository postsRepository;
  private final CommentsRepository commentsRepository;
  private final ReactionsRepository reactionsRepository;
  private final SocialUsersRepository socialUsersRepository;

  public FeedBuilder(PostsRepository postsRepository, CommentsRepository commentsRepository, ReactionsRepository reactionsRepository, SocialUsersRepository socialUsersRepository) {
    this.postsRepository = postsRepository;
    this.commentsRepository = commentsRepository;
    this.reactionsRepository = reactionsRepository;
    this.socialUsersRepository = socialUsersRepository;
  }

  public FeedDto constructCurrentUserFeed(int page, boolean oldestFirst) {
    SocialUser user = (SocialUser) SecurityContextHolder.getContext().getAuthentication();
    Sort sort = oldestFirst ? Sort.by("creationDate").descending() : Sort.by("creationDate").ascending();
    Pageable pageable = PageRequest.of(page, PAGE_SIZE, sort);
    List<Relation> relations = socialUsersRepository.findAllRelationsByUserId(user.getId());
    List<SocialUser> users = relations.stream().map(Relation::getRelation).toList();
    Page<Post> posts = postsRepository.findAllByCreatedByIn(users, pageable);
    return constructFeedDto(posts.getContent());
  }


  public FeedDto constructFeed(int page, boolean oldestFirst) {
    SocialUser user = (SocialUser) SecurityContextHolder.getContext().getAuthentication();
    Sort sort = oldestFirst ? Sort.by("creationDate").descending() : Sort.by("creationDate").ascending();
    Pageable pageable = PageRequest.of(page, PAGE_SIZE, sort);
    Page<Post> posts = postsRepository.findAllByCreatedBy(user, pageable);
    return constructFeedDto(posts.getContent());
  }

  private FeedDto constructFeedDto(List<Post> posts) {
    List<PostResponseDto> responsePosts = new ArrayList<>();
    List<UUID> userIds = new ArrayList<>();
    List<String> postIds = posts.stream().map(Post::getPostId).map(UUID::toString).toList();
    List<Comment> comments = commentsRepository.findAllByOriginalIds(postIds);
    List<String> commentIds = comments.stream().map(Comment::getCommentId).map(UUID::toString).toList();
    List<String> ids = Stream.concat(postIds.stream(), commentIds.stream()).toList();
    List<Reaction> reactions = reactionsRepository.findAllByParentIdIn(ids);
    for (Post post : posts) {
      PostResponseDto postResponseDto = new PostResponseDto();
      postResponseDto.setPostId(post.getPostId());
      postResponseDto.setCreatedBy(post.getCreatedBy().getId());
      postResponseDto.setContent(post.getContent());
      postResponseDto.setContentType(PostContentTypeDto.valueOf(post.getContentType().toString().toUpperCase()));
      postResponseDto.setTimestamp(post.getCreationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
      postResponseDto.setTaggedUserIds(post.getTaggedSocialUsers().stream().map(SocialUser::getId).toList());
      postResponseDto.setReactions(getPostReactions(post.getPostId(), reactions));
      postResponseDto.setComments(getPostComments(post.getPostId(), comments, reactions));
      responsePosts.add(postResponseDto);
    }
    FeedDto feedDto = new FeedDto();
    feedDto.setPosts(responsePosts);

    return feedDto;
  }

  private List<CommentResponseDto> getPostComments(UUID postId, List<Comment> comments, List<Reaction> reactions) {
    List<CommentResponseDto> responseComments = new ArrayList<>();
    comments = comments.stream().filter(comment -> comment.getPathToComment().startsWith(postId.toString())).toList();
    //comments.stream().map(comment -> comment.setPathToComment(comment.getPathToComment().s))
    CommentResponseDto commentResponseDto = new CommentResponseDto();
    for (Comment comment : comments) {

    }

    return responseComments;
  }

  private List<ReactionResponseDto> getPostReactions(UUID postId, List<Reaction> reactions) {
    return reactions.stream().filter(reaction -> reaction.getParentId().equals(postId.toString()))
        .map(this::reactionToReactionResponseDto).toList();
  }

  private ReactionResponseDto reactionToReactionResponseDto(Reaction reaction) {
    ReactionResponseDto reactionDto = new ReactionResponseDto();
    reactionDto.setUserId(reaction.getUserId().toString());
    reactionDto.setReactionType(ReactionTypeDto.valueOf(reaction.getReactionType().toString().toUpperCase()));
    return reactionDto;
  }
}
