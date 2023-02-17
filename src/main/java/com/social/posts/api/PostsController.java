package com.social.posts.api;

import com.social.api.PostsApi;
import com.social.model.PostPatchDto;
import com.social.model.PostPostDto;
import com.social.posts.model.Post;
import com.social.posts.service.PostsService;
import com.social.users.model.SocialUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class PostsController implements PostsApi {

  private final PostsService postsService;


  public PostsController(PostsService postsService) {
    this.postsService = postsService;
  }

  @Override
  public ResponseEntity<Void> deletePost(UUID postId) {
    SocialUser user = (SocialUser) SecurityContextHolder.getContext().getAuthentication();
    postsService.deletePost(postId, user.getId());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Void> postPost(PostPostDto postRequestDto) {
    Post post = postRequestDtoToPost(postRequestDto);
    postsService.createPost(post);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<Void> patchPost(UUID postId, PostPatchDto postPatchDto) {
    SocialUser user = (SocialUser) SecurityContextHolder.getContext().getAuthentication();
    if (postPatchDto.getContent() == null && postPatchDto.getIsPrivate() == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    if (postPatchDto.getContent() != null && postPatchDto.getContentType() != null && postPatchDto.getIsPrivate() != null) {
      postsService.updatePost(postId, user.getId(), postPatchDto.getContent(), getContentType(postPatchDto), postPatchDto.getIsPrivate());
      return new ResponseEntity<>(HttpStatus.OK);
    }
    if (postPatchDto.getContent() != null && postPatchDto.getContentType() != null) {
      postsService.updatePostContent(postId, user.getId(), postPatchDto.getContent(), getContentType(postPatchDto));
      return new ResponseEntity<>(HttpStatus.OK);
    }
    if (postPatchDto.getIsPrivate() != null) {
      postsService.updatePostIsPrivate(postId, user.getId(), postPatchDto.getIsPrivate());
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  private Post postRequestDtoToPost(PostPostDto postRequestDto) {
    SocialUser user = (SocialUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return Post.builder()
        .postId(UUID.randomUUID())
        .createdBy(new SocialUser(user.getId()))
        .content(postRequestDto.getContent())
        .contentType(getContentType(postRequestDto))
        .isPrivate(postRequestDto.getIsPrivate())
        .taggedSocialUsers(getTaggedUsers(postRequestDto))
        .build();
  }

  private Post.ContentType getContentType(PostPatchDto dto) {
    return Post.ContentType.valueOf(dto.getContentType().toString().toUpperCase());
  }

  private Post.ContentType getContentType(PostPostDto dto) {
    return Post.ContentType.valueOf(dto.getContentType().toString().toUpperCase());
  }

  private List<SocialUser> getTaggedUsers(PostPostDto dto) {
    return dto.getTaggedUsers().stream()
        .map(SocialUser::new)
        .collect(Collectors.toList());
  }
}
