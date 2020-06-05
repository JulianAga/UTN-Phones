package com.utn.phones.exceptions.phoneLinesExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PhoneLineNotExists extends ResponseStatusException {

  public PhoneLineNotExists() {
    super(HttpStatus.BAD_REQUEST, "the phone line doesn't exists.");
  }
}
