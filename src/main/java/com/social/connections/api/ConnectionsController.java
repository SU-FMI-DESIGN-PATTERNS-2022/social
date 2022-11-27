package com.social.connections.api;

import com.social.api.ConnectionsApi;
import com.social.model.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
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
