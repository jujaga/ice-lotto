package com.jrfom.icelotto.service;

import java.util.Collection;

import com.jrfom.icelotto.dto.PrizeItemDto;
import com.jrfom.icelotto.exception.PrizeItemNotFoundException;
import com.jrfom.icelotto.model.PrizeItem;

public interface PrizeItemService {
  public PrizeItem create(PrizeItemDto prizeItem);
  public PrizeItem delete(Long prizeItemId) throws PrizeItemNotFoundException;
  public Collection<PrizeItem> findAll();
  public PrizeItem findById(Long id);
  public PrizeItem update(PrizeItemDto prizeItem) throws PrizeItemNotFoundException;
}