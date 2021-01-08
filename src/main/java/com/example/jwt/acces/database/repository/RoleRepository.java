package com.example.jwt.acces.database.repository;

import com.example.jwt.acces.database.entitiy.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {

  Role findByName(String name);

}
