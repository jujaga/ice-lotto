package com.jrfom.icelotto.model;

// TODO: This needs to be redone. Just going to have to create a proper mapping
// from the noncompliant Spidy rarities to the GW2 API rarities
public enum ItemRarity {
  Junk(0),
  Basic(1), // GW2 API
  Common(1), // Spidy API
  Fine(2),
  Masterwork(3),
  Rare(4),
  Exotic(5),
  Ascended(6),
  Legendary(7);

  private final int number;

  private ItemRarity(int number) {
    this.number = number;
  }
}