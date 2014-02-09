package com.jrfom.icelotto.service;

import java.util.Collection;

import com.jrfom.icelotto.dto.DrawingResultDto;
import com.jrfom.icelotto.exception.DrawingResultNotFoundException;
import com.jrfom.icelotto.model.DrawingResult;

public interface DrawingResultService {
  public DrawingResult create(DrawingResultDto drawingResult);
  public DrawingResult delete(Long drawingResultId) throws DrawingResultNotFoundException;
  public Collection<DrawingResult> findAll();
  public DrawingResult findById(Long id);
  public DrawingResult update(DrawingResultDto drawingResult) throws DrawingResultNotFoundException;
}