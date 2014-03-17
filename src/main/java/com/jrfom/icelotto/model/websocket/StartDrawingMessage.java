package com.jrfom.icelotto.model.websocket;

public class StartDrawingMessage {
  private Long drawingId;

  public StartDrawingMessage() {}

  public Long getDrawingId() {
    return this.drawingId;
  }

  public void setDrawingId(Long drawingId) {
    this.drawingId = drawingId;
  }
}