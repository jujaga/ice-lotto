package com.jrfom.icelotto.model;

import javax.persistence.*;

@Entity
@Table(
  name = "shuffled_pool_entries"
)
public class ShuffledPoolEntry {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column
  private Integer position;

  @OneToOne
  @JoinColumn(referencedColumnName = "id")
  private Entry entry;

  @ManyToOne
  @JoinColumn(referencedColumnName = "id")
  private PrizePool prizePool;

  protected ShuffledPoolEntry() {}

  public ShuffledPoolEntry(PrizePool pool, Entry entry) {
    this.entry = entry;
    this.prizePool = pool;
  }

  public Long getId() {
    return this.id;
  }

  public Integer getPosition() {
    return this.position;
  }

  public void setPosition(Integer position) {
    this.position = position;
  }

  public Entry getEntry() {
    return this.entry;
  }

  public void setEntry(Entry entry) {
    this.entry = entry;
  }

  public PrizePool getPrizePool() {
    return this.prizePool;
  }

  public void setPrizePool(PrizePool prizePool) {
    this.prizePool = prizePool;
  }
}