package com.jrfom.icelotto.service;

import java.util.Collection;

import com.jrfom.icelotto.dto.RoleDto;
import com.jrfom.icelotto.exception.RoleNotFoundException;
import com.jrfom.icelotto.model.Role;

public interface RoleService {
  public Role create(RoleDto role);
  public Role delete(Long roleId) throws RoleNotFoundException;
  public Collection<Role> findAll();
  public Role findById(Long id);
  public Role update(RoleDto role) throws RoleNotFoundException;
}