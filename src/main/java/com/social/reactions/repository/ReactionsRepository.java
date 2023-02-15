package com.social.reactions.repository;

import com.social.reactions.model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReactionsRepository extends JpaRepository<Reaction, UUID> {

  boolean existsByParentIdAndUserId(UUID parentId,UUID userId);
}
