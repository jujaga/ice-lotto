package com.jrfom.icelotto.service;

import java.util.Collection;

import com.jrfom.icelotto.exception.CharacterNotFoundException;
import com.jrfom.icelotto.model.Character;

public interface CharacterService {
  Character create();
  Character delete(Long characterId) throws CharacterNotFoundException;
  Collection<Character> findAll();
  Character findById(Long id);
}