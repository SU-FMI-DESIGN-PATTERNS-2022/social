package com.social.users.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(name = "relations")
public class Relation {

  public enum RelationType {
    FRIEND,
    FOLLOWING
  }

  @Id
  @Column(name = "id")
  private UUID id;
  @ManyToOne(targetEntity = SocialUser.class)
  @JoinColumn(name = "owner", referencedColumnName = "user_id", nullable = false, insertable = false, updatable = false)
  private SocialUser owner;
  @ManyToOne(targetEntity = SocialUser.class)
  @JoinColumn(name = "relation", referencedColumnName = "user_id", nullable = false, insertable = false, updatable = false)
  private SocialUser relation;
  @Column(name = "relation_type")
  private RelationType relationType;


  public Relation() {
    this.id = UUID.randomUUID();
  }

}
