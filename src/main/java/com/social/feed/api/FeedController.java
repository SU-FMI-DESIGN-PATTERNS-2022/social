package com.social.feed.api;

import com.social.api.FeedApi;
import com.social.model.FeedDto;
import org.springframework.http.ResponseEntity;

public class FeedController implements FeedApi {

  @Override
  public ResponseEntity<FeedDto> feedFriendsGet() {
    return null;
  }

  @Override
  public ResponseEntity<FeedDto> getFeed(String userId) {
    return null;
  }
}
