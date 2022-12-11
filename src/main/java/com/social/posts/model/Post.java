package com.social.posts.model;

import com.social.users.model.SocialUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "posts")
@Data
@Builder
@AllArgsConstructor
public class Post {

  public enum ContentType {
    TEXT,
    IMAGE,
    VIDEO
  }

  @Id
  @Column(name = "post_id")
  private UUID postId;
  @ManyToOne
  @JoinColumn(name = "created_by")
  private SocialUser createdBy;
  @Column(name = "content")
  private String content;
  @Column(name = "content_type")
  private ContentType contentType;
  @Column(name = "is_private")
  private Boolean isPrivate;
  @ManyToMany(targetEntity = SocialUser.class)
  @JoinColumn(name = "tagged_users", referencedColumnName = " user_id", nullable = false, updatable = false, insertable = false)
  private List<SocialUser> taggedSocialUsers;
  @CreationTimestamp
  @Column(name = "created_on")
  private Date creationDate;

  public Post() {
    this.postId = UUID.randomUUID();
  }

}
