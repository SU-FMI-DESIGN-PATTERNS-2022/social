package com.social.users.repository;

import com.social.users.model.SocialUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SocialUsersRepository extends JpaRepository<SocialUser, UUID> {
}
