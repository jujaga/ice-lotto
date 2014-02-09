package com.jrfom.icelotto.service.impl;

import java.util.Collection;

import javax.annotation.Resource;

import com.jrfom.icelotto.dto.UserDto;
import com.jrfom.icelotto.exception.UserNotFoundException;
import com.jrfom.icelotto.model.User;
import com.jrfom.icelotto.repository.UserRepository;
import com.jrfom.icelotto.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryService implements UserService {
  private static final Logger log = LoggerFactory.getLogger(UserRepositoryService.class);

  @Resource
  private UserRepository userRepository;

  @Override
  public User create(UserDto user) {
    return null;
  }

  @Override
  public User delete(Long userId) throws UserNotFoundException {
    return null;
  }

  @Override
  public Collection<User> findAll() {
    return null;
  }

  @Override
  public User findById(Long id) {
    return null;
  }

  @Override
  public User update(UserDto user) throws UserNotFoundException {
    return null;
  }
}