package com.jrfom.icelotto.service;

import java.util.List;

import com.google.common.base.Optional;
import com.jrfom.icelotto.exception.DrawingResultNotFoundException;
import com.jrfom.icelotto.model.*;

public interface DrawingResultService {
  Optional<DrawingResult> create(Drawing drawing, PrizePool prizePool, PrizeTier prizeTier, PrizeItem prizeItem, User winner);
  void delete(Long drawingResultId) throws DrawingResultNotFoundException;
  List<DrawingResult> findAll();
  Optional<DrawingResult> findById(Long id);
}