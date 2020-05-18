package com.utn.phones.controllers;

import com.utn.phones.dto.ClientRequestDto;
import com.utn.phones.model.Client;
import com.utn.phones.services.ClientService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ClientController {

  private ClientService clientService;

  @Autowired
  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  @PostMapping("/")
  public Client save(@RequestBody ClientRequestDto client) {
    return this.clientService.save(client);
  }

  @GetMapping("/")
  public List<Client> findAll() {
    return this.clientService.findAll();
  }

}
