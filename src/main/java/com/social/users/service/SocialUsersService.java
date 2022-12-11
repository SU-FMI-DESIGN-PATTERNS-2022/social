package com.social.users.service;

import com.social.users.model.SocialUser;
import com.social.users.repository.SocialUsersRepository;

public class SocialUsersService {

  private final SocialUsersRepository socialUsersRepository;


  public SocialUsersService(SocialUsersRepository socialUsersRepository) {
    this.socialUsersRepository = socialUsersRepository;
  }

  public void createSocialUser(SocialUser user) {
    socialUsersRepository.save(user);
  }
}
