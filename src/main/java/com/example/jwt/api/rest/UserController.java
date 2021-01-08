package com.example.jwt.api.rest;

import com.example.jwt.acces.database.entitiy.User;
import com.example.jwt.domain.AccountAlreadyExist;
import com.example.jwt.domain.AccountManagementService;
import com.example.jwt.domain.InvalidEmail;
import com.example.jwt.domain.UserService;
import java.security.Principal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private UserService userService;
  private AccountManagementService accountManagementService;

  public UserController(UserService userService, AccountManagementService accountManagementService) {
    this.userService = userService;
    this.accountManagementService = accountManagementService;
  }

  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler(AccountAlreadyExist.class)
  public String accountAlreadyExist() {
    return "Email in use";
  }

  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler(InvalidEmail.class)
  public String invalidEmailException() {
    return "Invalid email";
  }

  @RequestMapping("/users")
  public List<User> getUsers(Principal principal) {
    Authentication a = (Authentication) principal;
    System.out.println(a.getCredentials().toString());
    return userService.getAllUser();
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PostMapping("/user")
  public void postUser(@RequestBody User user) {
    accountManagementService.registerUser(user);
  }

  @GetMapping("/user/{username}")
  public User getUserByUsername(@PathVariable String username) {
    return userService.getUserByUsername(username);
  }

}
