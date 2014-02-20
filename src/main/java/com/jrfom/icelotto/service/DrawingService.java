package com.jrfom.icelotto.service;

import java.util.List;

import com.google.common.base.Optional;
import com.jrfom.icelotto.exception.DrawingNotFoundException;
import com.jrfom.icelotto.model.Drawing;
import com.jrfom.icelotto.model.PrizePool;
import org.threeten.bp.Instant;

public interface DrawingService {
  Optional<Drawing> create(String name);
  Optional<Drawing> create(String name, Instant scheduled);
  Optional<Drawing> create(String name, Instant scheduled, PrizePool prizePool);
  void delete(Long drawingId) throws DrawingNotFoundException;
  List<Drawing> findAll();
  Optional<Drawing> findById(Long id);
}
