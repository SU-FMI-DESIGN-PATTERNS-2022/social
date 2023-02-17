package com.social.reactions.model;

public enum ReactionType {
    LIKE("like"),
    LOVE("love"),
    HAHA("haha"),
    WOW("wow"),
    SAD("haha"),
    ANGRY("angry");

    private final String name;

    ReactionType (String name) {
        this.name = name;
    }
}
