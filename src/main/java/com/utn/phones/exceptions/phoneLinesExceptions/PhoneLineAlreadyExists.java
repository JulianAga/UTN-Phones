package com.utn.phones.exceptions.phoneLinesExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PhoneLineAlreadyExists extends ResponseStatusException {

  public PhoneLineAlreadyExists() {
    super(HttpStatus.BAD_REQUEST, "the phone line already exists.");
  }
}