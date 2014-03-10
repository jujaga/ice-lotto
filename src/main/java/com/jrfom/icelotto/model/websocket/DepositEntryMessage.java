package com.jrfom.icelotto.model.websocket;

public class DepositEntryMessage {
  private Long drawingId;
  private Long poolId;
  private Integer amount;
  private String gw2DisplayName;

  public DepositEntryMessage() {}

  public Long getDrawingId() {
    return this.drawingId;
  }

  public void setDrawingId(Long drawingId) {
    this.drawingId = drawingId;
  }

  public Long getPoolId() {
    return this.poolId;
  }

  public void setPoolId(Long poolId) {
    this.poolId = poolId;
  }

  public Integer getAmount() {
    return this.amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public String getGw2DisplayName() {
    return this.gw2DisplayName;
  }

  public void setGw2DisplayName(String gw2DisplayName) {
    this.gw2DisplayName = gw2DisplayName;
  }
}