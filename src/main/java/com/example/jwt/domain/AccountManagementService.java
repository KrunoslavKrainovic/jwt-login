package com.example.jwt.domain;

import com.example.jwt.acces.database.entitiy.User;

public interface AccountManagementService {

  String login(User user);

  void registerUser(User user);

}
