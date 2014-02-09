package com.jrfom.icelotto.service;

import java.util.Collection;

import com.jrfom.icelotto.dto.DrawingDto;
import com.jrfom.icelotto.exception.DrawingNotFoundException;
import com.jrfom.icelotto.model.Drawing;

public interface DrawingService {
  public Drawing create(DrawingDto drawing);
  public Drawing delete(Long drawingId) throws DrawingNotFoundException;
  public Collection<Drawing> findAll();
  public Drawing findById(Long id);
  public Drawing update(DrawingDto drawing) throws DrawingNotFoundException;
}
