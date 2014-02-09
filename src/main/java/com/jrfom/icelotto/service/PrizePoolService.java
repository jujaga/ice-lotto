package com.jrfom.icelotto.service;

import java.util.Collection;

import com.jrfom.icelotto.dto.PrizePoolDto;
import com.jrfom.icelotto.exception.PrizePoolNotFoundException;
import com.jrfom.icelotto.model.PrizePool;

public interface PrizePoolService {
  public PrizePool create(PrizePoolDto prizePool);
  public PrizePool delete(Long prizePoolId) throws PrizePoolNotFoundException;
  public Collection<PrizePool> findAll();
  public PrizePool findById(Long id);
  public PrizePool update(PrizePoolDto prizePool) throws PrizePoolNotFoundException;
}
