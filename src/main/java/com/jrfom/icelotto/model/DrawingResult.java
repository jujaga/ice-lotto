package com.jrfom.icelotto.model;

import javax.persistence.*;

import com.jrfom.icelotto.util.Stringer;

@Entity
@Table(name = "drawing_results")
public class DrawingResult {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne
  @JoinColumn(referencedColumnName = "id")
  private Drawing drawing;

  @OneToOne
  @JoinColumn(referencedColumnName = "id")
  private User winner;

  @OneToOne
  @JoinColumn(referencedColumnName = "id")
  private PrizePool prizePool;

  @OneToOne
  @JoinColumn(referencedColumnName = "id")
  private PrizeTier prizeTier;

  @OneToOne
  @JoinColumn(referencedColumnName = "id")
  private PrizeItem prizeItem;

  public DrawingResult() {}

  public Long getId() {
    return this.id;
  }

  public Drawing getDrawing() {
    return this.drawing;
  }

  public void setDrawing(Drawing drawing) {
    this.drawing = drawing;
  }

  public User getWinner() {
    return this.winner;
  }

  public void setWinner(User winner) {
    this.winner = winner;
  }

  public PrizePool getPrizePool() {
    return this.prizePool;
  }

  public void setPrizePool(PrizePool prizePool) {
    this.prizePool = prizePool;
  }

  public PrizeTier getPrizeTier() {
    return this.prizeTier;
  }

  public void setPrizeTier(PrizeTier prizeTier) {
    this.prizeTier = prizeTier;
  }

  public PrizeItem getPrizeItem() {
    return this.prizeItem;
  }

  public void setPrizeItem(PrizeItem prizeItem) {
    this.prizeItem = prizeItem;
  }

  @Override
  public String toString() {
    return Stringer.jsonString(this);
  }
}