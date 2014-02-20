package com.jrfom.icelotto.service;

import java.util.Collection;

import com.jrfom.icelotto.exception.DrawingResultNotFoundException;
import com.jrfom.icelotto.model.DrawingResult;

public interface DrawingResultService {
  DrawingResult create();
  DrawingResult delete(Long drawingResultId) throws DrawingResultNotFoundException;
  Collection<DrawingResult> findAll();
  DrawingResult findById(Long id);
}