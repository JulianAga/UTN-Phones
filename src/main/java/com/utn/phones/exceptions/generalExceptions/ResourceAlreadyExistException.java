package com.utn.phones.exceptions.generalExceptions;

public class ResourceAlreadyExistException extends Exception {

  @Override
  public String getMessage() {
    return "Resource already exist";
  }
}
