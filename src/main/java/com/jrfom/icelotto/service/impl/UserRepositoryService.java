package com.jrfom.icelotto.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.google.common.base.Optional;
import com.jrfom.icelotto.exception.UserNotFoundException;
import com.jrfom.icelotto.model.User;
import com.jrfom.icelotto.repository.UserRepository;
import com.jrfom.icelotto.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRepositoryService implements UserService {
  private static final Logger log = LoggerFactory.getLogger(UserRepositoryService.class);

  @Resource
  private UserRepository userRepository;

  @Override
  @Transactional
  public Optional<User> create(String gw2DisplayName) {
    log.debug("Creating new user with gw2 name: `{}`", gw2DisplayName);
    Optional<User> result = Optional.absent();
    User record = new User(gw2DisplayName);

    try {
      record = this.userRepository.save(record);
      result = Optional.of(record);
    } catch (DataAccessException e) {
      log.error("Could not create new user: `{}`", e.getMessage());
      log.debug(e.toString());
    }

    return result;
  }

  @Override
  @Transactional(rollbackFor = UserNotFoundException.class)
  public void delete(Long userId) throws UserNotFoundException {
    log.debug("Deleting user with id: `{}`", userId);
    User deleted = this.userRepository.findOne(userId);

    if (deleted == null) {
      log.debug("Could not find user with id: `{}", userId);
      throw new UserNotFoundException();
    } else {
      this.userRepository.delete(userId);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public List<User> findAll() {
    log.debug("Finding all users");
    return this.userRepository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<User> findById(Long id) {
    log.debug("Finding user with id: `{}`", id);
    Optional<User> result = Optional.absent();
    User user = this.userRepository.findOne(id);

    if (user != null) {
      result = Optional.of(user);
    }

    return result;
  }
}