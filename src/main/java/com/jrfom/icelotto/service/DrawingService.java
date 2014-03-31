package com.jrfom.icelotto.service;

import java.util.List;

import com.google.common.base.Optional;
import com.jrfom.icelotto.exception.DrawingNotFoundException;
import com.jrfom.icelotto.model.Drawing;
import com.jrfom.icelotto.model.PrizePool;
import org.threeten.bp.Instant;

public interface DrawingService {
  Optional<Drawing> create(Instant scheduled);
  Optional<Drawing> create(Instant scheduled, PrizePool smallPool, PrizePool largePool);
  void delete(Long drawingId) throws DrawingNotFoundException;
  List<Drawing> findAll();
  Optional<Drawing> findById(Long id);

  /**
   * Retrieve the next scheduled drawing from the database.
   *
   * @return An instance of {@link com.jrfom.icelotto.model.Drawing} wrapped
   * in an {@link com.google.common.base.Optional} or an empty {@code Optional}
   * if one cannot be found.
   */
  Optional<Drawing> nextDrawing();

  Optional<Drawing> previousDrawing();

  Drawing save(Drawing drawing);
}
