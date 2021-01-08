package com.example.jwt.domain;

import com.example.jwt.acces.database.entitiy.Role;
import com.example.jwt.acces.database.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

  private RoleRepository roleRepository;

  public RoleServiceImpl(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public Role getRoleByName(String name) {
    return roleRepository.findByName(name);
  }
}
