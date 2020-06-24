package com.utn.phones.controllers;

import com.utn.phones.model.Client;
import com.utn.phones.services.IntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class Controller {

  @Autowired
  IntegrationService integrationService;

  @GetMapping("/pet")
  public Client getPet() {
    return integrationService.getPet();
  }
}