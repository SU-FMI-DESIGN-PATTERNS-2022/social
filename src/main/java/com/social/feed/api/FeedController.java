package com.social.feed.api;

import com.social.api.FeedApi;
import com.social.model.FeedDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class FeedController implements FeedApi {

  @PostMapping("/feed")
  public ResponseEntity<FeedDto> feedGet() {
    return null;
  }

  @Override
  public ResponseEntity<FeedDto> getFeed(String userId) {
    return null;
  }
}
