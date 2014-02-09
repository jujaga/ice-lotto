package com.jrfom.icelotto.dto;

import org.threeten.bp.Instant;

public class DrawingDto {
  private Long id;
  private Instant scheduled;
  private Instant held;
  private PrizePoolDto prizePool;
  private String name;

  public DrawingDto() {}

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Instant getScheduled() {
    return this.scheduled;
  }

  public void setScheduled(Instant scheduled) {
    this.scheduled = scheduled;
  }

  public Instant getHeld() {
    return this.held;
  }

  public void setHeld(Instant held) {
    this.held = held;
  }

  public PrizePoolDto getPrizePool() {
    return this.prizePool;
  }

  public void setPrizePool(PrizePoolDto prizePool) {
    this.prizePool = prizePool;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }
}