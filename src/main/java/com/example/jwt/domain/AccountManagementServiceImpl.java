package com.example.jwt.domain;

import com.example.jwt.acces.database.entitiy.Role;
import com.example.jwt.acces.database.entitiy.User;
import com.example.jwt.acces.database.repository.RoleRepository;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountManagementServiceImpl implements AccountManagementService {

  private PasswordEncoder passwordEncoder;

  private UserService userService;

  private RoleRepository roleRepository;


  public AccountManagementServiceImpl(PasswordEncoder passwordEncoder,
      UserService userService, RoleRepository roleRepository) {
    this.passwordEncoder = passwordEncoder;
    this.userService = userService;
    this.roleRepository = roleRepository;
  }

  @Override
  public String login(User user) {
    User userFromDB = userService.getUserByUsername(user.getUsername());


    if (!passwordEncoder.matches(user.getPassword(),userFromDB.getPassword())) {
      throw new IncorrectPassword();
    }
    return userFromDB.getPassword();

  }

  @Override
  public void registerUser(User user) {

    validateEmail(user.getUsername());

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    Role role = roleRepository.findByName("user");

    user.setRole(role);

    userService.createUser(user);

  }

  private final Pattern VALID_EMAIL_ADDRESS_REGEX =
      Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

  private boolean validateEmail(String emailStr) {
    Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);

    if(!matcher.find()){
      throw new InvalidEmail();
    }
    return matcher.find();
  }


}
