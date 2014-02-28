package com.jrfom.icelotto.model.websocket;

public class ItemAddMessage {
  private Integer count;
  private Long drawingId;
  private Long itemId;
  private Long poolId;
  private Long tierId;
  private Integer tierPosition;

  public ItemAddMessage() {}

  public Integer getCount() {
    return this.count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public Long getDrawingId() {
    return this.drawingId;
  }

  public void setDrawingId(Long drawingId) {
    this.drawingId = drawingId;
  }

  public Long getItemId() {
    return this.itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }

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

  public Integer getTierPosition() {
    return this.tierPosition;
  }

  public void setTierPosition(Integer tierPosition) {
    this.tierPosition = tierPosition;
  }
}