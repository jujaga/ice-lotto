package com.jrfom.icelotto.service;

import java.util.Collection;

import com.jrfom.icelotto.exception.DrawingNotFoundException;
import com.jrfom.icelotto.model.Drawing;

public interface DrawingService {
  public Drawing create();
  public Drawing delete(Long drawingId) throws DrawingNotFoundException;
  public Collection<Drawing> findAll();
  public Drawing findById(Long id);
}
