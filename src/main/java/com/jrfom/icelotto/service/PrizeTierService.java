package com.jrfom.icelotto.service;

import java.util.Collection;

import com.jrfom.icelotto.dto.PrizeTierDto;
import com.jrfom.icelotto.exception.PrizeTierNotFoundException;
import com.jrfom.icelotto.model.PrizeTier;

public interface PrizeTierService {
  public PrizeTier create(PrizeTierDto prizeTier);
  public PrizeTier delete(Long prizeTierId) throws PrizeTierNotFoundException;
  public Collection<PrizeTier> findAll();
  public PrizeTier findById(Long id);
  public PrizeTier update(PrizeTierDto prizeTier) throws PrizeTierNotFoundException;
}
