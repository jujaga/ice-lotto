package com.jrfom.icelotto.model;

import java.util.Set;

import javax.persistence.*;

import com.jrfom.icelotto.jpa.converters.InstantConverter;
import org.threeten.bp.Instant;

@Entity
@Table(name = "drawings")
public class Drawing {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(columnDefinition = "INTEGER")
  @Convert(converter = InstantConverter.class)
  private Instant scheduled;

  @Column(columnDefinition = "INTEGER")
  @Convert(converter = InstantConverter.class)
  private Instant held;

  @OneToOne
  @JoinColumn(referencedColumnName = "id")
  private PrizePool smallPool;

  @OneToOne
  @JoinColumn(referencedColumnName = "id")
  private PrizePool largePool;

  @OneToMany
  @JoinColumn(referencedColumnName = "id")
  private Set<Entry> entries;

  protected Drawing() {}

  public Drawing(Instant scheduled, PrizePool smallPool, PrizePool largePool) {
    this.scheduled = scheduled;
    this.smallPool = smallPool;
    this.largePool = largePool;
  }

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

  public PrizePool getSmallPool() {
    return this.smallPool;
  }

  public void setSmallPool(PrizePool prizePool) {
    this.smallPool = prizePool;
  }

  public PrizePool getLargePool() {
    return this.largePool;
  }

  public void setLargePool(PrizePool largePool) {
    this.largePool = largePool;
  }

}