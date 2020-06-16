package com.utn.phones.exceptions.loginExceptions;

public class InvalidLoginException extends Throwable {

  public String getMessage() {
    return "Invalid login";
  }
}
