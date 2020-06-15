package com.utn.phones.exceptions.loginExceptions;

public class InvalidLoginException extends Throwable {

  public InvalidLoginException(Throwable cause) {
    super(cause);
  }

  public InvalidLoginException() {

  }

  public String getMessage() {
    return "Invalid login";
  }
}
