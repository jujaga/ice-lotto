package com.jrfom.icelotto.model.websocket;

public class DrawForTierResponse {
  private Long poolId;
  private Long tierId;
  private Integer itemNumber;

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

  public Integer getItemNumber() {
    return this.itemNumber;
  }

  public void setItemNumber(Integer itemNumber) {
    this.itemNumber = itemNumber;
  }
}