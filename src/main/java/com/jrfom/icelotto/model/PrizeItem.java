package com.jrfom.icelotto.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.google.common.base.Preconditions;
import com.jrfom.icelotto.util.Stringer;

@Entity
@Table(name = "prize_items")
public class PrizeItem {
  @Id
  @Column(nullable = false)
  @NotNull
  private Long id;

  @Column
  private String name;

  @Column
  private String description;

  public PrizeItem() {}

  public PrizeItem(Long id) {
    this(id, null, null);
  }

  /**
   * Create an instance and set all properties. Note that {@code id} cannot be
   * {@code null}. If {@code id} is {@code null} then an
   * {@link java.lang.NullPointerException} will be thrown.
   *
   * @param id An identifier for the item.
   * @param name The name of the item.
   * @param description A decription of the item.
   *
   * @throws java.lang.NullPointerException
   */
  public PrizeItem(Long id, String name, String description) {
    Preconditions.checkNotNull(id, "Item id cannot be null");

    this.id = id;
    this.name = name;
    this.description = description;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void update(String name, String description) {
    this.name = name;
    this.description = description;
  }

  @Override
  public String toString() {
    return Stringer.jsonString(this);
  }
}