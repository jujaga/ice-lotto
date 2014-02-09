package com.jrfom.icelotto.model;

import javax.persistence.*;

@Entity
@Table(name = "drawing_results")
public class DrawingResult {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne
  @JoinColumn(name = "id")
  private Drawing drawing;

  @OneToOne
  @JoinColumn(name = "id")
  private User winner;

  @OneToOne
  @JoinColumn(name = "id")
  private PrizePool prizePool;

  @OneToOne
  @JoinColumn(name = "id")
  private PrizeTier prizeTier;

  @Column
  private Integer itemNumber;

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

  public void setWinner(User winnerId) {
    this.winner = winnerId;
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

  public Integer getItemNumber() {
    return this.itemNumber;
  }

  public void setItemNumber(Integer itemNumber) {
    this.itemNumber = itemNumber;
  }
}