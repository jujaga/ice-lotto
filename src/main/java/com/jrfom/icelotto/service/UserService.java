package com.jrfom.icelotto.service;

import java.util.List;

import com.google.common.base.Optional;
import com.jrfom.icelotto.exception.UserNotFoundException;
import com.jrfom.icelotto.model.User;

public interface UserService {
  Optional<User> create(String gw2DisplayName);
  void delete(Long userId) throws UserNotFoundException;
  List<User> findAll();
  Optional<User> findById(Long id);
  Optional<User> findByGw2DisplayName(String gw2DisplayName);
  List<User> findAllLike(String term);
  User save(User user);
}
