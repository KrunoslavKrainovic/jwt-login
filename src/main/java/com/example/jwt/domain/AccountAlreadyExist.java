package com.example.jwt.domain;

public class AccountAlreadyExist extends RuntimeException {

  public AccountAlreadyExist() {
  }

  public AccountAlreadyExist(String message) {
    super(message);
  }

  public AccountAlreadyExist(String message, Throwable cause) {
    super(message, cause);
  }

  public AccountAlreadyExist(Throwable cause) {
    super(cause);
  }

  public AccountAlreadyExist(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
