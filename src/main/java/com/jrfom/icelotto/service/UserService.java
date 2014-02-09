package com.jrfom.icelotto.service;

import java.util.Collection;

import com.jrfom.icelotto.dto.UserDto;
import com.jrfom.icelotto.exception.UserNotFoundException;
import com.jrfom.icelotto.model.User;

public interface UserService {
  public User create(UserDto user);
  public User delete(Long userId) throws UserNotFoundException;
  public Collection<User> findAll();
  public User findById(Long id);
  public User update(UserDto user) throws UserNotFoundException;
}
