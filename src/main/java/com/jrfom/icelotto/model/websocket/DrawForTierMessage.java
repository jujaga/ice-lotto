package com.jrfom.icelotto.model.websocket;

public class DrawForTierMessage {
  private Long poolId;
  private Long tierId;

  public DrawForTierMessage() {}

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
}