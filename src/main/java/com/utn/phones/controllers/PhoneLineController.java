package com.utn.phones.controllers;

import com.utn.phones.exceptions.phoneLinesExceptions.PhoneLineNotExists;
import com.utn.phones.model.PhoneLine;
import com.utn.phones.services.PhoneLineService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/phoneline")
public class PhoneLineController {

  PhoneLineService phoneLineService;

  @Autowired
  public PhoneLineController(PhoneLineService phoneLineService) {
    this.phoneLineService = phoneLineService;
  }

  @GetMapping("/")
  public List<PhoneLine> findAll() {
    return this.phoneLineService.findAll();
  }

  @PostMapping("/")
  public ResponseEntity<PhoneLine> save(@RequestBody PhoneLine phoneLine) {
    return ResponseEntity.created(getLocation(phoneLine)).build();
  }

  @GetMapping("/line")
  public ResponseEntity<PhoneLine> findByNumber(@RequestParam String number)
      throws PhoneLineNotExists {
    return ResponseEntity.ok(phoneLineService.findByNumber(number));
  }

  private URI getLocation(PhoneLine phoneLine) {
    return ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(phoneLine.getId())
        .toUri();
  }
}
