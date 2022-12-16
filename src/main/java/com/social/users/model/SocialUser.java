package com.social.users.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@Table(name = "social_users")
public class SocialUser {

  @Id
  @Column(name = "user_id")
  private UUID id;
  @Column(name = "first_name")
  private String firstName;
  @Column(name = "last_name")
  private String lastName;
  @OneToMany(targetEntity = Relation.class, mappedBy = "owner")
  private List<Relation> relations;

  public SocialUser(UUID id) {
    this.id = id;
  }

  public SocialUser() {
    this.id = UUID.randomUUID();
  }
}
