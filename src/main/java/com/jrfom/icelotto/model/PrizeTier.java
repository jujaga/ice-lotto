package com.jrfom.icelotto.model;

import javax.persistence.*;

@Entity
@Table(name = "prize_tiers")
public class PrizeTier {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne
  @JoinColumn(name = "id")
  private PrizeItem item1;

  @OneToOne
  @JoinColumn(name = "id")
  private PrizeItem item2;

  @OneToOne
  @JoinColumn(name = "id")
  private PrizeItem item3;

  @OneToOne
  @JoinColumn(name = "id")
  private PrizeItem item4;

  @OneToOne
  @JoinColumn(name = "id")
  private PrizeItem item5;

  @OneToOne
  @JoinColumn(name = "id")
  private PrizeItem item6;

  @OneToOne
  @JoinColumn(name = "id")
  private PrizeItem item7;

  @OneToOne
  @JoinColumn(name = "id")
  private PrizeItem item8;

  @OneToOne
  @JoinColumn(name = "id")
  private PrizeItem item9;

  @OneToOne
  @JoinColumn(name = "id")
  private PrizeItem item10;

  public PrizeTier() {}

  public Long getId() {
    return this.id;
  }

  public PrizeItem getItem1() {
    return this.item1;
  }

  public void setItem1(PrizeItem item1) {
    this.item1 = item1;
  }

  public PrizeItem getItem2() {
    return this.item2;
  }

  public void setItem2(PrizeItem item2) {
    this.item2 = item2;
  }

  public PrizeItem getItem3() {
    return this.item3;
  }

  public void setItem3(PrizeItem item3) {
    this.item3 = item3;
  }

  public PrizeItem getItem4() {
    return this.item4;
  }

  public void setItem4(PrizeItem item4) {
    this.item4 = item4;
  }

  public PrizeItem getItem5() {
    return this.item5;
  }

  public void setItem5(PrizeItem item5) {
    this.item5 = item5;
  }

  public PrizeItem getItem6() {
    return this.item6;
  }

  public void setItem6(PrizeItem item6) {
    this.item6 = item6;
  }

  public PrizeItem getItem7() {
    return this.item7;
  }

  public void setItem7(PrizeItem item7) {
    this.item7 = item7;
  }

  public PrizeItem getItem8() {
    return this.item8;
  }

  public void setItem8(PrizeItem item8) {
    this.item8 = item8;
  }

  public PrizeItem getItem9() {
    return this.item9;
  }

  public void setItem9(PrizeItem item9) {
    this.item9 = item9;
  }

  public PrizeItem getItem10() {
    return this.item10;
  }

  public void setItem10(PrizeItem item10) {
    this.item10 = item10;
  }
}