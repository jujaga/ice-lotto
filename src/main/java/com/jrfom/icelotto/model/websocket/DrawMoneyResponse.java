package com.jrfom.icelotto.model.websocket;

import com.jrfom.icelotto.model.MoneyDrawResult;

public class DrawMoneyResponse {
  private Long poolId;
  private MoneyDrawResult result;

  public DrawMoneyResponse() {}

  public Long getPoolId() {
    return this.poolId;
  }

  public void setPoolId(Long poolId) {
    this.poolId = poolId;
  }

  public MoneyDrawResult getResult() {
    return this.result;
  }

  public void setResult(MoneyDrawResult result) {
    this.result = result;
  }
}