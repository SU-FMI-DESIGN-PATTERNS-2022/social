package com.social.comments.model;

import com.social.model.ParentType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comments")
public class Comment {

  @Id
  @Column(name = "comment_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private String id;
  @Column(name = "user_id")
  private String userId;
  @Column(name = "parent_id")
  private String parentId;
  @Column(name = "content")
  private String content;
  @Column(name = "content_type")
  private CommentContentType contentType;
  @Column(name = "parent_type")
  private ParentType parentType;

  public Comment() {

  }

  public Comment(String userId, String parentId, String content, CommentContentType contentType, ParentType parentType) {
    this.userId = userId;
    this.parentId = parentId;
    this.content = content;
    this.contentType = contentType;
    this.parentType = parentType;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public String getUserId() {
    return userId;
  }

  public String getParentId() {
    return parentId;
  }

  public String getContent() {
    return content;
  }

  public CommentContentType getContentType() {
    return contentType;
  }

  public ParentType getParentType() {
    return parentType;
  }
}
