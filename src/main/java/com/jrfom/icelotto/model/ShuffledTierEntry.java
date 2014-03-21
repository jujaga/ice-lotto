package com.jrfom.icelotto.model;

import javax.persistence.*;

@Entity
@Table(
  name = "shuffled_tier_entries"
)
public class ShuffledTierEntry {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column
  private Integer position;

  @ManyToOne
  @JoinColumn(referencedColumnName = "id")
  private PrizeTier prizeTier;

  @ManyToOne
  @JoinColumn(referencedColumnName = "id")
  private Entry entry;

  protected ShuffledTierEntry() {}

  public ShuffledTierEntry(PrizeTier prizeTier, Entry entry) {
    this.prizeTier = prizeTier;
    this.entry = entry;
  }

  public Long getId() {
    return this.id;
  }

  public Integer getPosition() {
    return this.position;
  }

  public void setPosition(Integer order) {
    this.position = order;
  }

  public PrizeTier getPrizeTier() {
    return this.prizeTier;
  }

  public void setPrizeTier(PrizeTier prizeTier) {
    this.prizeTier = prizeTier;
  }

  public Entry getEntry() {
    return this.entry;
  }

  public void setEntry(Entry entry) {
    this.entry = entry;
  }
}