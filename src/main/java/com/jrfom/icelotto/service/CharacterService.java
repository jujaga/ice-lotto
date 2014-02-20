package com.jrfom.icelotto.service;

import java.util.Collection;

import com.jrfom.icelotto.exception.CharacterNotFoundException;
import com.jrfom.icelotto.model.Character;

public interface CharacterService {
  public Character create();
  public Character delete(Long characterId) throws CharacterNotFoundException;
  public Collection<Character> findAll();
  public Character findById(Long id);
}