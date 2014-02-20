package com.jrfom.icelotto.model;

import java.util.List;

import javax.persistence.*;

import com.jrfom.icelotto.util.Stringer;

@Entity
@Table(name = "prize_tiers")
public class PrizeTier {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne
  @JoinColumn(referencedColumnName = "id")
  private PrizeItem item1;

  @OneToOne
  @JoinColumn(referencedColumnName = "id")
  private PrizeItem item2;

  @OneToOne
  @JoinColumn(referencedColumnName = "id")
  private PrizeItem item3;

  @OneToOne
  @JoinColumn(referencedColumnName = "id")
  private PrizeItem item4;

  @OneToOne
  @JoinColumn(referencedColumnName = "id")
  private PrizeItem item5;

  @OneToOne
  @JoinColumn(referencedColumnName = "id")
  private PrizeItem item6;

  @OneToOne
  @JoinColumn(referencedColumnName = "id")
  private PrizeItem item7;

  @OneToOne
  @JoinColumn(referencedColumnName = "id")
  private PrizeItem item8;

  @OneToOne
  @JoinColumn(referencedColumnName = "id")
  private PrizeItem item9;

  @OneToOne
  @JoinColumn(referencedColumnName = "id")
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

  /**
   * <p>Updates the record by setting the item identifiers to those in the
   * {@code prizeItems} list. This is done by iterating the list, so list element
   * 0 corresponds to {@code item1} and list element 9 to {@code item10}.</p>
   *
   * <p>If you supply a list of 5 elements, only the first 5 items will be
   * updated.</p>
   *
   * <p>If an item is to be removed, then supply {@code null} for the value.</p>
   *
   * @param prizeItems A {@link java.util.List} of
   *                  {@link com.jrfom.icelotto.model.PrizeItem}s. An
   *                  {@link java.util.ArrayList} should be used since it supports
   *                  {@code null} values.
   */
  public void update(List<PrizeItem> prizeItems) {
    for (int i = 0, j = prizeItems.size(); i < j; i += 1) {
      switch (i) {
        case 0:
          this.setItem1(prizeItems.get(i));
          break;
        case 1:
          this.setItem2(prizeItems.get(i));
          break;
        case 2:
          this.setItem3(prizeItems.get(i));
          break;
        case 3:
          this.setItem4(prizeItems.get(i));
          break;
        case 4:
          this.setItem5(prizeItems.get(i));
          break;
        case 5:
          this.setItem6(prizeItems.get(i));
          break;
        case 6:
          this.setItem7(prizeItems.get(i));
          break;
        case 7:
          this.setItem8(prizeItems.get(i));
          break;
        case 8:
          this.setItem9(prizeItems.get(i));
          break;
        case 9:
          this.setItem10(prizeItems.get(i));
          break;
        default:
      }
    }
  }

  @Override
  public String toString() {
    return Stringer.jsonString(this);
  }
}