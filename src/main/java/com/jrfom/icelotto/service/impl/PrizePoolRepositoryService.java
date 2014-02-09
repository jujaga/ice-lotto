package com.jrfom.icelotto.service.impl;

import java.util.Collection;

import javax.annotation.Resource;

import com.jrfom.icelotto.dto.PrizePoolDto;
import com.jrfom.icelotto.exception.PrizePoolNotFoundException;
import com.jrfom.icelotto.model.PrizePool;
import com.jrfom.icelotto.repository.PrizePoolRepository;
import com.jrfom.icelotto.service.PrizePoolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PrizePoolRepositoryService implements PrizePoolService {
  private static final Logger log = LoggerFactory.getLogger(PrizePoolRepositoryService.class);

  @Resource
  private PrizePoolRepository prizePoolRepository;

  @Override
  public PrizePool create(PrizePoolDto prizePool) {
    return null;
  }

  @Override
  public PrizePool delete(Long prizePoolId) throws PrizePoolNotFoundException {
    return null;
  }

  @Override
  public Collection<PrizePool> findAll() {
    return null;
  }

  @Override
  public PrizePool findById(Long id) {
    return null;
  }

  @Override
  public PrizePool update(PrizePoolDto prizePool) throws PrizePoolNotFoundException {
    return null;
  }
}