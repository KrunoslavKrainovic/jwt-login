package com.example.jwt.api.rest;

import com.example.jwt.acces.database.entitiy.User;
import com.example.jwt.domain.AccountManagementService;
import com.example.jwt.domain.IncorrectPassword;
import com.example.jwt.domain.JwtTokenGenerator;
import com.example.jwt.domain.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

  private AccountManagementService accountManagementService;
  private JwtTokenGenerator jwtTokenGenerator;

  public AuthController(AccountManagementService accountManagementService, JwtTokenGenerator jwtTokenGenerator) {
    this.accountManagementService = accountManagementService;
    this.jwtTokenGenerator = jwtTokenGenerator;
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(UserNotFoundException.class)
  public String userNotFound() {
    return "Username or password is wrong";
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(IncorrectPassword.class)
  public String incorrectPassword() {
    return "Username or password is wrong";
  }

  @PostMapping("/authentication")
  public String createToken(@RequestBody User user) {

    String username = accountManagementService.login(user);

    return jwtTokenGenerator.createToken(username, "ROLE_USER");
  }

}
