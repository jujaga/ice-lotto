package com.jrfom.icelotto.service.impl;

import java.util.Collection;

import javax.annotation.Resource;

import com.jrfom.icelotto.dto.DrawingDto;
import com.jrfom.icelotto.exception.DrawingNotFoundException;
import com.jrfom.icelotto.model.Drawing;
import com.jrfom.icelotto.repository.DrawingRepository;
import com.jrfom.icelotto.service.DrawingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DrawingRepositoryService implements DrawingService {
  private static final Logger log = LoggerFactory.getLogger(DrawingRepositoryService.class);

  @Resource
  private DrawingRepository drawingRepository;

  @Override
  public Drawing create(DrawingDto drawing) {
    return null;
  }

  @Override
  public Drawing delete(Long drawingId) throws DrawingNotFoundException {
    return null;
  }

  @Override
  public Collection<Drawing> findAll() {
    return null;
  }

  @Override
  public Drawing findById(Long id) {
    return null;
  }

  @Override
  public Drawing update(DrawingDto drawing) throws DrawingNotFoundException {
    return null;
  }
}