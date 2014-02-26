package com.jrfom.icelotto.model.websocket;

public class ItemSearchResponse {
  private Object content;

  public ItemSearchResponse(Object content) {
    this.content = content;
  }

  public Object getContent() {
    return this.content;
  }
}