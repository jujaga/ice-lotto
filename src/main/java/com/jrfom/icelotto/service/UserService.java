package com.jrfom.icelotto.service;

import java.util.Collection;

import com.jrfom.icelotto.exception.UserNotFoundException;
import com.jrfom.icelotto.model.User;

public interface UserService {
  User create();
  User delete(Long userId) throws UserNotFoundException;
  Collection<User> findAll();
  User findById(Long id);
}
