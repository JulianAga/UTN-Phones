package com.utn.phones.exceptions.loginExceptions;

public class RecordExistsException extends Exception {

  public String getMessage() {
    return "Record already exist";
  }


}
