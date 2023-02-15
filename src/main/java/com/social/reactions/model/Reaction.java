package com.social.reactions.model;


import com.social.model.ParentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reactions")
public class Reaction {

  @Id
  @Column(name = "reactions_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID reactionId;
  @Column(name = "user_id")
  private UUID userId;
  @Column(name = "parent_id")
  private UUID parentId;
  @Column(name = "parent_type")
  private ParentType parentType;
  @Column(name = "reaction_type")
  private ReactionType reactionType;

}
