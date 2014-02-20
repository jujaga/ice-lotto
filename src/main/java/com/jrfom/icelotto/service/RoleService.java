package com.jrfom.icelotto.service;

import java.util.List;

import com.google.common.base.Optional;
import com.jrfom.icelotto.exception.RoleNotFoundException;
import com.jrfom.icelotto.model.Role;

public interface RoleService {
  Optional<Role> create(String name, String description);
  void delete(Long roleId) throws RoleNotFoundException;
  List<Role> findAll();
  Optional<Role> findById(Long id);
}