package com.jrfom.icelotto.model.external.spidy;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpidyItemSearchResponse {
  private Integer count;
  private Integer page;
  @JsonProperty("last_page")
  private Integer lastPage;
  private Integer total;
  private List<SpidyItem> results;

  public SpidyItemSearchResponse() {}

  public Integer getCount() {
    return this.count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public Integer getPage() {
    return this.page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getLastPage() {
    return this.lastPage;
  }

  public void setLastPage(Integer lastPage) {
    this.lastPage = lastPage;
  }

  public Integer getTotal() {
    return this.total;
  }

  public void setTotal(Integer total) {
    this.total = total;
  }

  public List<SpidyItem> getResults() {
    return this.results;
  }

  public void setResults(List<SpidyItem> results) {
    this.results = results;
  }
}