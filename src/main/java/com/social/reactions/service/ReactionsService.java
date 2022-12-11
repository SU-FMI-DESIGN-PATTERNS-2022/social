package com.social.reactions.service;


import com.social.reactions.model.Reaction;
import com.social.reactions.model.ReactionType;
import com.social.reactions.repository.ReactionsRepository;

import java.util.UUID;

public class ReactionsService {

    private final ReactionsRepository reactionsRepository;

    public ReactionsService(ReactionsRepository reactionsRepository) {
        this.reactionsRepository = reactionsRepository;
    }

    public boolean doesParentExist(String parentId) {
        return reactionsRepository.existsById(parentId);
    }

    public void createReaction(Reaction reaction) {
        reactionsRepository.save(reaction);
    }

    public void deleteReaction(String userId, String parentId) {
        reactionsRepository.deleteByParentIdAndUserId(parentId, userId);
    }

    public void updateReactionContent(String parentId, String userId, ReactionType reactionType) {
        reactionsRepository.updateContentTypeByParentIdAndUserId(parentId,userId,reactionType);
    }
}
