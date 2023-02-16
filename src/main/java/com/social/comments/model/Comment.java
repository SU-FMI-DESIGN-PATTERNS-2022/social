package com.social.comments.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment {

  @Id
  @Column(name = "comment_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID commentId;
  @Column(name = "user_id")
  private UUID userId;
  @Column(name = "path_to_comment")
  private String pathToComment;
  @Lob
  @Column(name = "content")
  private byte[] content;
  @Column(name = "content_type")
  private CommentContentType contentType;
}
