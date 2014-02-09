package com.jrfom.icelotto.model;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Embeddable
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column
  private String email;

  @Column
  private String displayName;

  @Column(
    nullable = false,
    unique = true
  )
  private String gw2DisplayName;

  @Column
  private String password;

  @Column
  private String salt;

  @OneToMany
  @JoinTable(
    name = "user_characters",
    joinColumns = {
      @JoinColumn(name = "user_id")
    },
    inverseJoinColumns = {
      @JoinColumn(name = "character_id")
    }
  )
  private Set<Character> characters;

  @ManyToMany
  @JoinTable(
    name = "user_roles",
    joinColumns = {
      @JoinColumn(name = "user_id")
    },
    inverseJoinColumns = {
      @JoinColumn(name = "role_id")
    }
  )
  private Set<Role> roles;

  public User() {}

  public Long getId() {
    return this.id;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDisplayName() {
    return this.displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getGw2DisplayName() {
    return this.gw2DisplayName;
  }

  public void setGw2DisplayName(String gw2DisplayName) {
    this.gw2DisplayName = gw2DisplayName;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getSalt() {
    return this.salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public Set<Role> getRoles() {
    return this.roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }
}