package com.example.jwt.domain;

import com.example.jwt.acces.database.entitiy.User;
import java.util.List;

public interface UserService {


  void createUser(User user);

  User getUserByUsername(String username) throws UserNotFoundException;

  List<User> getAllUser();

}
