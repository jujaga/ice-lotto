package com.jrfom.icelotto.service.impl;

import java.util.Collection;

import javax.annotation.Resource;

import com.jrfom.icelotto.exception.DrawingResultNotFoundException;
import com.jrfom.icelotto.model.DrawingResult;
import com.jrfom.icelotto.repository.DrawingResultRepository;
import com.jrfom.icelotto.service.DrawingResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DrawingResultRepositoryService implements DrawingResultService {
  private static final Logger log = LoggerFactory.getLogger(DrawingResultRepositoryService.class);

  @Resource
  private DrawingResultRepository drawingResultRepository;

  @Override
  public DrawingResult create() {
    return null;
  }

  @Override
  public DrawingResult delete(Long drawingResultId) throws DrawingResultNotFoundException {
    return null;
  }

  @Override
  public Collection<DrawingResult> findAll() {
    return null;
  }

  @Override
  public DrawingResult findById(Long id) {
    return null;
  }
}