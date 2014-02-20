package com.jrfom.icelotto.service;

import java.util.Collection;

import com.jrfom.icelotto.exception.RoleNotFoundException;
import com.jrfom.icelotto.model.Role;

public interface RoleService {
  Role create();
  Role delete(Long roleId) throws RoleNotFoundException;
  Collection<Role> findAll();
  Role findById(Long id);
}