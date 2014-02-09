package com.jrfom.icelotto.dto;

import java.util.Set;

import com.jrfom.icelotto.model.User;

public class RoleDto {
  private Long id;
  private String name;
  private String description;
  private Set<User> users;

  public RoleDto() {}

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Set<User> getUsers() {
    return this.users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }
}