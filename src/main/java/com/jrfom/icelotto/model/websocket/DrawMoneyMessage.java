package com.jrfom.icelotto.model.websocket;

public class DrawMoneyMessage {
  private Long drawingId;
  private String poolName; // "small" or "large"

  public DrawMoneyMessage() {}

  public Long getDrawingId() {
    return this.drawingId;
  }

  public void setDrawingId(Long drawingId) {
    this.drawingId = drawingId;
  }

  public String getPoolName() {
    return this.poolName;
  }

  public void setPoolName(String poolName) {
    this.poolName = poolName;
  }

  public boolean isSmallPool() {
    return this.poolName.toLowerCase().equals("small");
  }
}