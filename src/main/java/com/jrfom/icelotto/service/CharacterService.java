package com.jrfom.icelotto.service;

import java.util.List;

import com.google.common.base.Optional;
import com.jrfom.icelotto.exception.CharacterNotFoundException;
import com.jrfom.icelotto.model.Character;

public interface CharacterService {
  Optional<Character> create(String name);
  void delete(Long characterId) throws CharacterNotFoundException;
  List<Character> findAll();
  Optional<Character> findById(Long id);
}