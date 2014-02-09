package com.jrfom.icelotto.dto;

public class DrawingResultDto {
  private Long id;
  private DrawingDto drawing;
  private UserDto winner;
  private PrizePoolDto prizePool;
  private PrizeTierDto prizeTier;
  private Integer itemNumber;

  public DrawingResultDto() {}

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public DrawingDto getDrawing() {
    return this.drawing;
  }

  public void setDrawing(DrawingDto drawing) {
    this.drawing = drawing;
  }

  public UserDto getWinner() {
    return this.winner;
  }

  public void setWinner(UserDto winner) {
    this.winner = winner;
  }

  public PrizePoolDto getPrizePool() {
    return this.prizePool;
  }

  public void setPrizePool(PrizePoolDto prizePool) {
    this.prizePool = prizePool;
  }

  public PrizeTierDto getPrizeTier() {
    return this.prizeTier;
  }

  public void setPrizeTier(PrizeTierDto prizeTier) {
    this.prizeTier = prizeTier;
  }

  public Integer getItemNumber() {
    return this.itemNumber;
  }

  public void setItemNumber(Integer itemNumber) {
    this.itemNumber = itemNumber;
  }
}