package com.jrfom.icelotto.dto;

import java.util.Set;

public class UserDto {
  private Long id;
  private String email;
  private String displayName;
  private String gw2DisplayName;
  private String password;
  private String salt;
  private Set<RoleDto> roles;

  public UserDto() {}

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Set<RoleDto> getRoles() {
    return this.roles;
  }

  public void setRoles(Set<RoleDto> roles) {
    this.roles = roles;
  }
}