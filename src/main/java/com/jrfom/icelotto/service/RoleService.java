package com.jrfom.icelotto.service;

import java.util.Collection;

import com.jrfom.icelotto.exception.RoleNotFoundException;
import com.jrfom.icelotto.model.Role;

public interface RoleService {
  public Role create();
  public Role delete(Long roleId) throws RoleNotFoundException;
  public Collection<Role> findAll();
  public Role findById(Long id);
}