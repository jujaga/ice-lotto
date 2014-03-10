package com.jrfom.icelotto.model.websocket;

import java.util.List;

public class UserSearchResult {
  private Long userId;
  private String gw2DisplayName;
  private String name;
  private List<String> tokens;

  public UserSearchResult() {}

  public Long getUserId() {
    return this.userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getGw2DisplayName() {
    return this.gw2DisplayName;
  }

  public void setGw2DisplayName(String gw2DisplayName) {
    this.gw2DisplayName = gw2DisplayName;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getTokens() {
    return this.tokens;
  }

  public void setTokens(List<String> tokens) {
    this.tokens = tokens;
  }
}