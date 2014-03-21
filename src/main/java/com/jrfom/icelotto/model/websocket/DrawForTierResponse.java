package com.jrfom.icelotto.model.websocket;

import com.jrfom.icelotto.model.PrizeDrawResult;

public class DrawForTierResponse {
  private Long poolId;
  private Long tierId;
  private PrizeDrawResult result;

  public DrawForTierResponse() {}

  public Long getPoolId() {
    return this.poolId;
  }

  public void setPoolId(Long poolId) {
    this.poolId = poolId;
  }

  public Long getTierId() {
    return this.tierId;
  }

  public void setTierId(Long tierId) {
    this.tierId = tierId;
  }

  public PrizeDrawResult getResult() {
    return this.result;
  }

  public void setResult(PrizeDrawResult result) {
    this.result = result;
  }
}