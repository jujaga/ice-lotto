package com.jrfom.icelotto.model.websocket;

public class StartDrawingResponse {
  private Long drawingId;
  private boolean started;

  public StartDrawingResponse() {
    this.drawingId = -1l;
    this.started = false;
  }

  public Long getDrawingId() {
    return this.drawingId;
  }

  public void setDrawingId(Long drawingId) {
    this.drawingId = drawingId;
  }

  public boolean isStarted() {
    return this.started;
  }

  public void setStarted(boolean started) {
    this.started = started;
  }
}