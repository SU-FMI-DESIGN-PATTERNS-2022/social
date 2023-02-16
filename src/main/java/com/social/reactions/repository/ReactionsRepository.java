package com.social.reactions.repository;

import com.social.reactions.model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReactionsRepository extends JpaRepository<Reaction, UUID> {

  boolean existsByParentIdAndUserId(String parentId,UUID userId);

  void deleteAllByParentIdIn(List<String> parentIds);
}
