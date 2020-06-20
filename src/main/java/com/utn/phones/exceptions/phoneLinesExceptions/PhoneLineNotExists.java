package com.utn.phones.exceptions.phoneLinesExceptions;

import com.utn.phones.exceptions.generalExceptions.ResourceNotFoundException;

public class PhoneLineNotExists extends ResourceNotFoundException {

  public String getMessage() {
    return " the phone line doesn't exists.";
  }
}
