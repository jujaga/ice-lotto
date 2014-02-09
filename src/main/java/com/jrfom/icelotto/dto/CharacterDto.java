package com.jrfom.icelotto.dto;

public class CharacterDto {
  private Long id;
  private String name;

  public CharacterDto() {}

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
}