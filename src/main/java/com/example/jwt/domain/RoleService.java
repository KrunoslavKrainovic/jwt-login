package com.example.jwt.domain;

import com.example.jwt.acces.database.entitiy.Role;

public interface RoleService {


  Role getRoleByName(String name);

}
