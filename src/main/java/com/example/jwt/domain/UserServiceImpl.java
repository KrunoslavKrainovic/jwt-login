package com.example.jwt.domain;

import com.example.jwt.acces.database.entitiy.User;
import com.example.jwt.acces.database.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


  private UserRepository userRepository;


  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }


  @Override
  public void createUser(User user) {

    if(userRepository.existsByUsername(user.getUsername())){
      throw new AccountAlreadyExist();
    }

    userRepository.save(user);
  }

  @Override
  public User getUserByUsername(String username){

    User user = userRepository.findByUsername(username);

    if(user == null ){
      throw new UserNotFoundException();
    }

    return userRepository.findByUsername(username);

  }

  @Override
  public List<User> getAllUser() {
    return userRepository.findAll();
  }


}
