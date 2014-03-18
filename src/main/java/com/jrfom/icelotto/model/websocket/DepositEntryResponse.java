package com.jrfom.icelotto.model.websocket;

public class DepositEntryResponse {
  private Integer smallPoolTotal;
  private Integer largePoolTotal;

  public DepositEntryResponse() {
    this.smallPoolTotal = -1;
    this.largePoolTotal = -1;
  }

  public Integer getSmallPoolTotal() {
    return this.smallPoolTotal;
  }

  public void setSmallPoolTotal(Integer smallPoolTotal) {
    this.smallPoolTotal = smallPoolTotal;
  }

  public Integer getLargePoolTotal() {
    return this.largePoolTotal;
  }

  public void setLargePoolTotal(Integer largePoolTotal) {
    this.largePoolTotal = largePoolTotal;
  }
}