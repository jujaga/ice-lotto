package com.jrfom.icelotto.model;

public class ClaimTicket {
  private String gw2DisplayName;
  private String claimKey;

  public ClaimTicket() {}

  public String getGw2DisplayName() {
    return this.gw2DisplayName;
  }

  public void setGw2DisplayName(String gw2DisplayName) {
    this.gw2DisplayName = gw2DisplayName;
  }

  public String getClaimKey() {
    return this.claimKey;
  }

  public void setClaimKey(String claimKey) {
    this.claimKey = claimKey;
  }
}