package com.jrfom.icelotto.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(referencedColumnName = "id")
  private PrizePool smallPool;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(referencedColumnName = "id")
  private PrizePool largePool;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(
    name = "drawing_entries",
    joinColumns = {
      @JoinColumn(name = "drawing_id")
    },
    inverseJoinColumns = {
      @JoinColumn(name = "entry_id")
    }
  )
  private Set<Entry> entries;

  protected Drawing() {
    this.entries = new HashSet<>(0);
  }

  public Drawing(Instant scheduled, PrizePool smallPool, PrizePool largePool) {
    this.scheduled = scheduled;
    this.smallPool = smallPool;
    this.largePool = largePool;
  }

  public Long getId() {
    return this.id;
  }

  /**
   * For unit testing.
   *
   * @param id A number as an identifier.
   */
  protected void setId(Long id) {
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

  public Set<Entry> getEntries() {
    return this.entries;
  }

  @Transient
  public Integer getSmallPoolTotal() {
    Integer result = 0;
    List<Entry> countableEntries = new ArrayList<>(0);

    for (Entry entry : this.entries) {
      if (entry.getUser().hasEntriesInDrawing(this) &&
        entry.getUser().isInSmallPoolForDrawing(this))
      {
        countableEntries.add(entry);
      }
    }

    for (Entry entry : countableEntries) {
      result += entry.getAmount();
    }

    return result;
  }

  @Transient
  public Integer getLargePoolTotal() {
    Integer result = 0;
    List<Entry> countableEntries = new ArrayList<>(0);

    for (Entry entry : this.entries) {
      if (entry.getUser().hasEntriesInDrawing(this) &&
        entry.getUser().isInLargePoolForDrawing(this))
      {
        countableEntries.add(entry);
      }
    }

    for (Entry entry : countableEntries) {
      result += entry.getAmount();
    }

    return result;
  }

  @Transient
  public void addEntry(Entry entry) {
    this.entries.add(entry);
  }
}