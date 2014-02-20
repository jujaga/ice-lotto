package com.jrfom.icelotto.service.impl;

import java.util.Collection;

import javax.annotation.Resource;

import com.jrfom.icelotto.exception.RoleNotFoundException;
import com.jrfom.icelotto.model.Role;
import com.jrfom.icelotto.repository.RoleRepository;
import com.jrfom.icelotto.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RoleRepositoryService implements RoleService {
  private static final Logger log = LoggerFactory.getLogger(RoleRepositoryService.class);

  @Resource
  private RoleRepository roleRepository;

  @Override
  public Role create() {
    return null;
  }

  @Override
  public Role delete(Long roleId) throws RoleNotFoundException {
    return null;
  }

  @Override
  public Collection<Role> findAll() {
    return null;
  }

  @Override
  public Role findById(Long id) {
    return null;
  }
}