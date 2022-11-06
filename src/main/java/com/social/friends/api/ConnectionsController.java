package com.social.friends.api;

import com.social.api.ConnectionsApi;
import com.social.model.UserDto;
import org.springframework.http.ResponseEntity;

public class ConnectionsController implements ConnectionsApi {

  @Override
  public ResponseEntity<UserDto> friendsGet() {
    return null;
  }

  @Override
  public ResponseEntity<Void> friendsPost() {
    return null;
  }
}
