package com.social.reactions.api;

import com.social.api.ReactionsApi;
import com.social.model.ParentType;
import com.social.model.ReactionDto;
import com.social.reactions.model.Reaction;
import com.social.reactions.model.ReactionType;
import com.social.reactions.service.ReactionsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ReactionsController implements ReactionsApi {
  private final ReactionsService reactionsService;

  public ReactionsController(ReactionsService reactionsService) {
    this.reactionsService = reactionsService;
  }

  @Override
  public ResponseEntity<Void> deleteReactionsReactionId(String parentId) {
    String userId = "JFDOJ";
    reactionsService.deleteReaction(userId,parentId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<ReactionDto> putReactions(ReactionDto reactionDto) {
    Reaction reaction = peactionDtoToReaction(reactionDto);
    if(!reactionsService.doesParentExist(reaction.getParentId())) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    reactionsService.createReaction(reaction);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  private Reaction peactionDtoToReaction(ReactionDto reactionDto)
  {
    String userId = "JFDOJ";
    String parentId = reactionDto.getParentId();
    ParentType parentType = ParentType.valueOf(reactionDto.getParentType());
    ReactionType reactionType = ReactionType.valueOf(reactionDto.getReactionType().toString());
    return new Reaction(userId,parentId,parentType,reactionType);
  }
}
