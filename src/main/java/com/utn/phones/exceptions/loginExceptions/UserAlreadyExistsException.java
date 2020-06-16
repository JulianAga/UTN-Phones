package com.utn.phones.exceptions.loginExceptions;

public class UserAlreadyExistsException extends RecordExistsException {

  public String getMessage() {
    return "User already exist";
  }

}
