package com.example.jwt.acces.database.repository;

import com.example.jwt.acces.database.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

  User findByUsername(String username);

  boolean existsByUsername(String username);

}
