package com.utn.phones.controllers;

import com.utn.phones.exceptions.phoneLinesExceptions.PhoneLineNotExists;
import com.utn.phones.model.PhoneLine;
import com.utn.phones.services.PhoneLineService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class PhoneLineController {

  PhoneLineService phoneLineService;

  @Autowired
  public PhoneLineController(PhoneLineService phoneLineService) {
    this.phoneLineService = phoneLineService;
  }

  public List<PhoneLine> findAll() {
    return this.phoneLineService.findAll();
  }

  public ResponseEntity<PhoneLine> save(@RequestBody PhoneLine phoneLine) {
    return ResponseEntity.created(getLocation(phoneLine)).build();
  }

  public ResponseEntity<PhoneLine> findByNumber(@RequestParam String number)
      throws PhoneLineNotExists {
    return ResponseEntity.ok(this.phoneLineService.findByNumber(number));
  }

  public void deleteById(Integer id) {
    this.phoneLineService.deleteById(id);
  }

  public void suspendPhoneLine(Integer id){
    this.phoneLineService.suspendPhoneLine(id);
  }

  private URI getLocation(PhoneLine phoneLine) {
    return ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(phoneLine.getId())
        .toUri();
  }
}
