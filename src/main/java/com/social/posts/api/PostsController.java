package com.social.posts.api;

import com.social.api.PostsApi;
import com.social.model.PostRequestDto;
import com.social.model.PostResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class PostsController implements PostsApi {

  @Override
  public ResponseEntity<Void> deletePostsPostId(String postId) {
    return null;
  }

  @Override
  public ResponseEntity<PostResponseDto> putPosts(PostRequestDto postRequestDto) {
    return null;
  }
}
