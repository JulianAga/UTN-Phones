package com.utn.phones.exceptions.phoneLinesExceptions;

public class PhoneLineNotExists extends Exception {

  public String getMessage() {
    return "the phone line doesn't exists.";
  }
}
