package com.social.reactions.model;


import com.social.model.ParentType;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "reactions")
public class Reaction {

    @Id
    @Column(name = "reactions_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID reactionId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "parent_id")
    private String parentId;
    @Column(name = "parent_type")
    private ParentType parentType;
    @Column(name = "reaction_type")
    private ReactionType reactionType;

    public Reaction() {
    }

    public Reaction(String userId, String parentId, ParentType parentType, ReactionType reactionType) {
        this.userId = userId;
        this.parentId = parentId;
        this.parentType = parentType;
        this.reactionType = reactionType;
    }

    public Reaction(UUID reactionId, String userId, String parentId, ReactionType reactionType, ParentType parentType) {
        this.reactionId = reactionId;
        this.userId = userId;
        this.parentId = parentId;
        this.reactionType = reactionType;
        this.parentType = parentType;
    }

    public void setReactionId(UUID reactionId) {
        this.reactionId = reactionId;
    }

    public UUID getReactionId() {
        return reactionId;
    }

    public String getUserId() {
        return userId;
    }

    public String getParentId() {
        return parentId;
    }

    public ReactionType getReactionType() {
        return reactionType;
    }

    public ParentType getParentType() {
        return parentType;
    }
}
