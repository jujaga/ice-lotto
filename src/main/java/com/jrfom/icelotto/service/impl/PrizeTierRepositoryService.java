package com.jrfom.icelotto.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.google.common.base.Optional;
import com.jrfom.icelotto.exception.PrizeTierNotFoundException;
import com.jrfom.icelotto.model.PrizeTier;
import com.jrfom.icelotto.repository.PrizeTierRepository;
import com.jrfom.icelotto.service.PrizeTierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrizeTierRepositoryService implements PrizeTierService {
  private static final Logger log = LoggerFactory.getLogger(PrizeTierRepositoryService.class);

  @Resource
  private PrizeTierRepository prizeTierRepository;

  @Override
  @Transactional
  public Optional<PrizeTier> create() {
    log.debug("Creating new prize tier");
    Optional<PrizeTier> result = Optional.absent();
    PrizeTier record = new PrizeTier();

    try {
      record = this.prizeTierRepository.save(record);
      result = Optional.of(record);
    } catch (DataAccessException e) {
      log.error("Something went wrong creating prize tier: `{}`", e.getMessage());
      log.debug(e.toString());
    }

    return result;
  }

  @Override
  @Transactional(rollbackFor = PrizeTierNotFoundException.class)
  public void delete(Long prizeTierId) throws PrizeTierNotFoundException {
    log.debug("Deleting prize tier with id: `{}`", prizeTierId);
    PrizeTier deleted = this.prizeTierRepository.findOne(prizeTierId);

    if (deleted == null) {
      log.debug("Could not find prize tier with id: `{}`", prizeTierId);
      throw new PrizeTierNotFoundException();
    } else {
      this.prizeTierRepository.delete(prizeTierId);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public List<PrizeTier> findAll() {
    log.debug("Finding all prize tiers");
    return this.prizeTierRepository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<PrizeTier> findById(Long id) {
    log.debug("Finding prize tier with id: `{}`", id);
    Optional<PrizeTier> result = Optional.absent();
    PrizeTier tier = this.prizeTierRepository.findOne(id);

    if (tier != null) {
      result = Optional.of(tier);
    }

    return result;
  }
}