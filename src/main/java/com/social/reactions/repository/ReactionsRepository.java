package com.social.reactions.repository;

import com.social.reactions.model.Reaction;
import com.social.reactions.model.ReactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionsRepository extends JpaRepository<Reaction, String> {
    void deleteByParentIdAndUserId(String parentId,String userId);

    @Modifying
    @Query("UPDATE Reaction c SET c.reactionType = ?6 WHERE c.parentId=?1 AND c.userId =?1")
    void updateContentTypeByParentIdAndUserId(String patentId, String userId, ReactionType contentType);
}
