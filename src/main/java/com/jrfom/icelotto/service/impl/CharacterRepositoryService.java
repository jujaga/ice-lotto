package com.jrfom.icelotto.service.impl;

import java.util.Collection;

import javax.annotation.Resource;

import com.jrfom.icelotto.dto.CharacterDto;
import com.jrfom.icelotto.exception.CharacterNotFoundException;
import com.jrfom.icelotto.model.Character;
import com.jrfom.icelotto.repository.CharacterRepository;
import com.jrfom.icelotto.service.CharacterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CharacterRepositoryService implements CharacterService {
  private static final Logger log = LoggerFactory.getLogger(CharacterRepositoryService.class);

  @Resource
  private CharacterRepository characterRepository;

  @Override
  public com.jrfom.icelotto.model.Character create(CharacterDto character) {
    return null;
  }

  @Override
  public Character delete(Long characterId) throws CharacterNotFoundException {
    return null;
  }

  @Override
  public Collection<Character> findAll() {
    return null;
  }

  @Override
  public Character findById(Long id) {
    return null;
  }

  @Override
  public Character update(CharacterDto character) throws CharacterNotFoundException {
    return null;
  }
}