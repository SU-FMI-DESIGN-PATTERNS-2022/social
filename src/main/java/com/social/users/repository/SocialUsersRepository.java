package com.social.users.repository;

import com.social.users.model.Relation;
import com.social.users.model.SocialUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SocialUsersRepository extends JpaRepository<SocialUser, UUID> {

  @Query("SELECT r FROM SocialUser u JOIN u.relations r WHERE u.id = :userId")
  List<Relation> findAllRelationsByUserId(@Param("userId") UUID userId);
}
