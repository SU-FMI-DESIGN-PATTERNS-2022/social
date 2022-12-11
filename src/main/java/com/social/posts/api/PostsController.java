package com.social.posts.api;

import com.social.api.PostsApi;
import com.social.model.PostPatchDto;
import com.social.model.PostPostDto;
import com.social.posts.model.Post;
import com.social.posts.service.PostsService;
import com.social.users.model.SocialUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    UUID userId = UUID.randomUUID();
    postsService.deletePost(postId, userId);
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
    UUID userId = UUID.randomUUID();
    if (postPatchDto.getContent() == null && postPatchDto.getIsPrivate() == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    if (postPatchDto.getContent() != null && postPatchDto.getContentType() != null && postPatchDto.getIsPrivate() != null) {
      postsService.updatePost(postId, userId, postPatchDto.getContent(), getContentType(postPatchDto), postPatchDto.getIsPrivate());
      return new ResponseEntity<>(HttpStatus.OK);
    }
    if (postPatchDto.getContent() != null && postPatchDto.getContentType() != null) {
      postsService.updatePostContent(postId, userId, postPatchDto.getContent(), getContentType(postPatchDto));
      return new ResponseEntity<>(HttpStatus.OK);
    }
    if (postPatchDto.getIsPrivate() != null) {
      postsService.updatePostIsPrivate(postId, userId, postPatchDto.getIsPrivate());
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  private Post postRequestDtoToPost(PostPostDto postRequestDto) {
    UUID createdBy = UUID.randomUUID();
    return Post.builder()
        .postId(UUID.randomUUID())
        .createdBy(new SocialUser(createdBy))
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
