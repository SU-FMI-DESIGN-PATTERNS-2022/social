package com.social.comments.model;

public enum CommentContentType {
  TEXT("text"),
  GIF("gif"),
  IMAGE("image"),
  DELETED("deleted");

  private final String name;

  CommentContentType(String name) {
    this.name = name;
  }
}
