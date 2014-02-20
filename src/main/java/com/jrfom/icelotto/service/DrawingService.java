package com.jrfom.icelotto.service;

import java.util.Collection;

import com.jrfom.icelotto.exception.DrawingNotFoundException;
import com.jrfom.icelotto.model.Drawing;

public interface DrawingService {
  Drawing create();
  Drawing delete(Long drawingId) throws DrawingNotFoundException;
  Collection<Drawing> findAll();
  Drawing findById(Long id);
}
