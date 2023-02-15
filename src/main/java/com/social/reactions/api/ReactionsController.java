package com.social.reactions.api;

import com.social.api.ReactionsApi;
import com.social.model.ReactionDto;
import com.social.reactions.model.Reaction;
import com.social.reactions.model.ReactionType;
import com.social.reactions.service.ReactionsService;
import com.social.users.model.SocialUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/")
public class ReactionsController implements ReactionsApi {
  private final ReactionsService reactionsService;

  public ReactionsController(ReactionsService reactionsService) {
    this.reactionsService = reactionsService;
  }

  @Override
  public ResponseEntity<Void> deleteReaction(UUID reactionId) {
    SocialUser user = (SocialUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    reactionsService.deleteReaction(user.getId(), reactionId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Void> postReaction(ReactionDto reactionDto) {
    Reaction reaction = reactionDtoToReaction(reactionDto);
    reactionsService.createReaction(reaction);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<ReactionDto> putReaction(UUID reactionId, ReactionDto reactionDto) {
    Reaction reaction = reactionDtoToReaction(reactionDto);
    reaction.setReactionId(reactionId);
    if (!reactionsService.doesReactionExist(reaction.getReactionId())) {
      throw new IllegalArgumentException("Reaction does not exist");
    }
    reactionsService.updateReactionType(reaction);
    return new ResponseEntity<>(HttpStatus.OK);

  }

  private Reaction reactionDtoToReaction(ReactionDto reactionDto) {
    SocialUser user = (SocialUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return Reaction.builder()
        .userId(user.getId())
        .parentId(reactionDto.getParentId())
        .parentType(reactionDto.getParentType())
        .reactionType(ReactionType.valueOf(reactionDto.getReactionType().toString().toUpperCase()))
        .build();
  }
}
