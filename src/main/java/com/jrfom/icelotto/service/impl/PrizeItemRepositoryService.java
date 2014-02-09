package com.jrfom.icelotto.service.impl;

import java.util.Collection;

import javax.annotation.Resource;

import com.jrfom.icelotto.dto.PrizeItemDto;
import com.jrfom.icelotto.exception.PrizeItemNotFoundException;
import com.jrfom.icelotto.model.PrizeItem;
import com.jrfom.icelotto.repository.PrizeItemRepository;
import com.jrfom.icelotto.service.PrizeItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PrizeItemRepositoryService implements PrizeItemService {
  private static final Logger log = LoggerFactory.getLogger(PrizeItemRepositoryService.class);

  @Resource
  private PrizeItemRepository prizeItemRepository;

  @Override
  public PrizeItem create(PrizeItemDto prizeItem) {
    return null;
  }

  @Override
  public PrizeItem delete(Long prizeItemId) throws PrizeItemNotFoundException {
    return null;
  }

  @Override
  public Collection<PrizeItem> findAll() {
    return null;
  }

  @Override
  public PrizeItem findById(Long id) {
    return null;
  }

  @Override
  public PrizeItem update(PrizeItemDto prizeItem) throws PrizeItemNotFoundException {
    return null;
  }
}