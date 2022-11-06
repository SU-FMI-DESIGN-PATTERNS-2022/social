package com.social.reactions.api;

import com.social.api.ReactionsApi;
import com.social.model.ReactionDto;
import org.springframework.http.ResponseEntity;

public class ReactionsController implements ReactionsApi {

  @Override
  public ResponseEntity<Void> deleteReactionsReactionId(String reactionId) {
    return null;
  }

  @Override
  public ResponseEntity<ReactionDto> putReactions(ReactionDto reactionDto) {
    return null;
  }
}
