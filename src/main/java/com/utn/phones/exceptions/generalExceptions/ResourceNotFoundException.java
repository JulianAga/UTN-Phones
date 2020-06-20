package com.utn.phones.exceptions.generalExceptions;

public class ResourceNotFoundException extends Exception {

  @Override
  public String getMessage() {
    return "Resource not found";
  }
}
