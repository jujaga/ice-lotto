package com.jrfom.icelotto.service;

import java.util.Collection;

import com.jrfom.icelotto.exception.DrawingResultNotFoundException;
import com.jrfom.icelotto.model.DrawingResult;

public interface DrawingResultService {
  public DrawingResult create();
  public DrawingResult delete(Long drawingResultId) throws DrawingResultNotFoundException;
  public Collection<DrawingResult> findAll();
  public DrawingResult findById(Long id);
}