package com.jrfom.icelotto.model.external.spidy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jrfom.icelotto.deserializers.SpidyLastChangedTimeDeserializer;
import com.jrfom.icelotto.serializers.InstantSerializer;
import org.threeten.bp.Instant;

public class SpidyItem {
  @JsonProperty("data_id")
  private Integer dataId;
  private String name;
  private Integer rarity;
  @JsonProperty("restriction_level")
  private Integer restrictionLevel;
  private String img;
  @JsonProperty("type_id")
  private Integer typeId;
  @JsonProperty("sub_type_id")
  private Integer subTypeId;
  @JsonProperty("price_last_changed")
  @JsonDeserialize(using = SpidyLastChangedTimeDeserializer.class)
  @JsonSerialize(using = InstantSerializer.class)
  private Instant priceLastChanged;
  @JsonProperty("max_offer_unit_price")
  private Integer maxOfferUnitPrice;
  @JsonProperty("min_sale_unit_price")
  private Integer minSaleUnitPrice;
  @JsonProperty("offer_availability")
  private Integer offerAvailability;
  @JsonProperty("sale_availability")
  private Integer saleAvailability;
  @JsonProperty("sale_price_change_last_hour")
  private Integer salePriceChangeLastHour;
  @JsonProperty("offer_price_change_last_hour")
  private Integer offerPriceChangeLastHour;

  public SpidyItem() {}

  public Integer getDataId() {
    return this.dataId;
  }

  public void setDataId(Integer dataId) {
    this.dataId = dataId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getRarity() {
    return this.rarity;
  }

  public void setRarity(Integer rarity) {
    this.rarity = rarity;
  }

  public Integer getRestrictionLevel() {
    return this.restrictionLevel;
  }

  public void setRestrictionLevel(Integer restrictionLevel) {
    this.restrictionLevel = restrictionLevel;
  }

  public String getImg() {
    return this.img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public Integer getTypeId() {
    return this.typeId;
  }

  public void setTypeId(Integer typeId) {
    this.typeId = typeId;
  }

  public Integer getSubTypeId() {
    return this.subTypeId;
  }

  public void setSubTypeId(Integer subTypeId) {
    this.subTypeId = subTypeId;
  }

  public Instant getPriceLastChanged() {
    return this.priceLastChanged;
  }

  public void setPriceLastChanged(Instant priceLastChanged) {
    this.priceLastChanged = priceLastChanged;
  }

  public Integer getMaxOfferUnitPrice() {
    return this.maxOfferUnitPrice;
  }

  public void setMaxOfferUnitPrice(Integer maxOfferUnitPrice) {
    this.maxOfferUnitPrice = maxOfferUnitPrice;
  }

  public Integer getMinSaleUnitPrice() {
    return this.minSaleUnitPrice;
  }

  public void setMinSaleUnitPrice(Integer minSaleUnitPrice) {
    this.minSaleUnitPrice = minSaleUnitPrice;
  }

  public Integer getOfferAvailability() {
    return this.offerAvailability;
  }

  public void setOfferAvailability(Integer offerAvailability) {
    this.offerAvailability = offerAvailability;
  }

  public Integer getSaleAvailability() {
    return this.saleAvailability;
  }

  public void setSaleAvailability(Integer saleAvailability) {
    this.saleAvailability = saleAvailability;
  }

  public Integer getSalePriceChangeLastHour() {
    return this.salePriceChangeLastHour;
  }

  public void setSalePriceChangeLastHour(Integer salePriceChangeLastHour) {
    this.salePriceChangeLastHour = salePriceChangeLastHour;
  }

  public Integer getOfferPriceChangeLastHour() {
    return this.offerPriceChangeLastHour;
  }

  public void setOfferPriceChangeLastHour(Integer offerPriceChangeLastHour) {
    this.offerPriceChangeLastHour = offerPriceChangeLastHour;
  }
}