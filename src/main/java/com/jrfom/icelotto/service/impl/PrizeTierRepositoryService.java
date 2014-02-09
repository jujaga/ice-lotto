package com.jrfom.icelotto.service.impl;

import java.util.Collection;

import javax.annotation.Resource;

import com.jrfom.icelotto.dto.PrizeTierDto;
import com.jrfom.icelotto.exception.PrizeTierNotFoundException;
import com.jrfom.icelotto.model.PrizeTier;
import com.jrfom.icelotto.repository.PrizeTierRepository;
import com.jrfom.icelotto.service.PrizeTierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PrizeTierRepositoryService implements PrizeTierService {
  private static final Logger log = LoggerFactory.getLogger(PrizeTierRepositoryService.class);

  @Resource
  private PrizeTierRepository prizeTierRepository;

  @Override
  public PrizeTier create(PrizeTierDto prizeTier) {
    return null;
  }

  @Override
  public PrizeTier delete(Long prizeTierId) throws PrizeTierNotFoundException {
    return null;
  }

  @Override
  public Collection<PrizeTier> findAll() {
    return null;
  }

  @Override
  public PrizeTier findById(Long id) {
    return null;
  }

  @Override
  public PrizeTier update(PrizeTierDto prizeTier) throws PrizeTierNotFoundException {
    return null;
  }
}