package com.jrfom.icelotto.model.websocket;

public class ItemAddResponse {
  private boolean successful;
  private String message;

  public ItemAddResponse() {
    this.successful = true;
  }

  public ItemAddResponse(boolean successful, String message) {
    this.successful = successful;
    this.message = message;
  }

  public boolean isSuccessful() {
    return this.successful;
  }

  public void setSuccessful(boolean successful) {
    this.successful = successful;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}