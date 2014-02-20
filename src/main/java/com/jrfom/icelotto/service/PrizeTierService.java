package com.jrfom.icelotto.service;

import java.util.List;

import com.google.common.base.Optional;
import com.jrfom.icelotto.exception.PrizeTierNotFoundException;
import com.jrfom.icelotto.model.PrizeTier;

public interface PrizeTierService {
  public Optional<PrizeTier> create();
  public void delete(Long prizeTierId) throws PrizeTierNotFoundException;
  public List<PrizeTier> findAll();
  public Optional<PrizeTier> findById(Long id);
}
