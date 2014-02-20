package com.jrfom.icelotto.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.google.common.base.Optional;
import com.jrfom.icelotto.exception.DrawingResultNotFoundException;
import com.jrfom.icelotto.model.*;
import com.jrfom.icelotto.repository.DrawingResultRepository;
import com.jrfom.icelotto.service.DrawingResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DrawingResultRepositoryService implements DrawingResultService {
  private static final Logger log = LoggerFactory.getLogger(DrawingResultRepositoryService.class);

  @Resource
  private DrawingResultRepository drawingResultRepository;

  @Override
  @Transactional
  public Optional<DrawingResult> create(
    Drawing drawing,
    PrizePool prizePool,
    PrizeTier prizeTier,
    PrizeItem prizeItem,
    User winner)
  {
    log.debug("Creating new drawing result");
    Optional<DrawingResult> result = Optional.absent();
    DrawingResult record = new DrawingResult();

    record.setDrawing(drawing);
    record.setPrizePool(prizePool);
    record.setPrizeTier(prizeTier);
    record.setPrizeItem(prizeItem);
    record.setWinner(winner);

    try {
      record = this.drawingResultRepository.save(record);
      result = Optional.of(record);
    } catch (DataAccessException e) {
      log.error("Could not create new drawing result: `{}`", e.getMessage());
      log.debug(e.toString());
    }

    return result;
  }

  @Override
  @Transactional(rollbackFor = DrawingResultNotFoundException.class)
  public void delete(Long drawingResultId) throws DrawingResultNotFoundException {
    log.debug("Deleting drawing result with id: `{}`", drawingResultId);
    DrawingResult deleted = this.drawingResultRepository.findOne(drawingResultId);

    if (deleted == null) {
      log.debug("Could not find drawing result with id: `{}`", drawingResultId);
      throw new DrawingResultNotFoundException();
    } else {
      this.drawingResultRepository.delete(drawingResultId);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public List<DrawingResult> findAll() {
    log.debug("Finding all drawing results");
    return this.drawingResultRepository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<DrawingResult> findById(Long id) {
    log.debug("Finding drawing result with id: `{}`", id);
    Optional<DrawingResult> result = Optional.absent();
    DrawingResult drawingResult = this.drawingResultRepository.findOne(id);

    if (drawingResult != null) {
      result = Optional.of(drawingResult);
    }

    return result;
  }
}