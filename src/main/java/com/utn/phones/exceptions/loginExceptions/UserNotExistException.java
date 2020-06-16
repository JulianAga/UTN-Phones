package com.utn.phones.exceptions.loginExceptions;

public class UserNotExistException extends Exception {

  public String getMessage() {
    return "User doesn't exist";
  }
}
