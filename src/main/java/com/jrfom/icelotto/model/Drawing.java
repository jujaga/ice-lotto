package com.jrfom.icelotto.model;

import javax.persistence.*;

import org.threeten.bp.Instant;

@Entity
@Table(name = "drawings")
public class Drawing {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(columnDefinition = "TIMESTAMP")
  private Instant scheduled;

  @Column(columnDefinition = "TIMESTAMP")
  private Instant held;

  @OneToOne
  @JoinColumn(referencedColumnName = "id")
  private PrizePool prizePool;

  @Column
  private String name;

  public Drawing() {}

  public Long getId() {
    return this.id;
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

  public PrizePool getPrizePool() {
    return this.prizePool;
  }

  public void setPrizePool(PrizePool prizePool) {
    this.prizePool = prizePool;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }
}