package com.social.reactions.service;


import com.social.comments.repository.CommentsRepository;
import com.social.model.ParentType;
import com.social.posts.repository.PostsRepository;
import com.social.reactions.model.Reaction;
import com.social.reactions.repository.ReactionsRepository;
import com.social.users.model.SocialUser;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

public class ReactionsService {

  private static final String REACTION_NOT_FOUND_MSG = "Reaction does not exist";
  private static final String CAN_NOT_MODIFY_REACTION = "Can not modify another person's reaction";
  private static final String PARENT_DOES_NOT_EXIST = "Parent does not exist";

  private final ReactionsRepository reactionsRepository;
  private final PostsRepository postsRepository;
  private final CommentsRepository commentsRepository;

  public ReactionsService(ReactionsRepository reactionsRepository, PostsRepository postsRepository, CommentsRepository commentsRepository) {
    this.reactionsRepository = reactionsRepository;
    this.postsRepository = postsRepository;
    this.commentsRepository = commentsRepository;
  }

  public boolean doesReactionExist(UUID parentId) {
    return reactionsRepository.existsById(parentId);
  }

  public void createReaction(Reaction reaction) {
    if (reactionsRepository.existsByParentIdAndUserId(reaction.getParentId(), reaction.getUserId())) {
      throw new IllegalArgumentException("Reaction for resource already exists.");
    }
    checkIfParentExists(reaction.getParentId(), reaction.getParentType());
    reactionsRepository.save(reaction);
  }

  public void checkIfParentExists(String parentId, ParentType parentType) {
    switch (parentType) {
      case POST -> {
        if (!postsRepository.existsById(getUUID(parentId))) {
          throw new IllegalArgumentException(PARENT_DOES_NOT_EXIST);
        }
      }
      case COMMENT -> {
        if (commentsRepository.existsById(getUUID(parentId))) {
          throw new IllegalArgumentException(PARENT_DOES_NOT_EXIST);
        }
      }
      case NEWS -> {
      }
    }
  }

  private UUID getUUID(String id) {
    try {
      return UUID.fromString(id);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("parentId not in uuid format");
    }
  }

  public void deleteReaction(UUID userId, UUID reactionId) {
    Optional<Reaction> optionalReaction = reactionsRepository.findById(reactionId);
    if (optionalReaction.isEmpty()) {
      throw new IllegalArgumentException(REACTION_NOT_FOUND_MSG);
    }
    Reaction reaction = optionalReaction.get();
    if (!reaction.getUserId().equals(userId)) {
      throw new IllegalCallerException(CAN_NOT_MODIFY_REACTION);
    }
    reactionsRepository.deleteById(reactionId);
  }

  public void updateReactionType(Reaction reaction) {
    SocialUser user = (SocialUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Optional<Reaction> optionalReaction = reactionsRepository.findById(reaction.getReactionId());
    if (optionalReaction.isEmpty()) {
      throw new IllegalArgumentException(REACTION_NOT_FOUND_MSG);
    }
    Reaction toUpdate = optionalReaction.get();

    if (!user.getId().equals(toUpdate.getUserId())) {
      throw new IllegalCallerException(CAN_NOT_MODIFY_REACTION);
    }
    toUpdate.setReactionType(reaction.getReactionType());

    reactionsRepository.save(toUpdate);
  }
}
