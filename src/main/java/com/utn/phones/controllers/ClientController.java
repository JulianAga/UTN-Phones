package com.utn.phones.controllers;

import com.utn.phones.model.Client;
import com.utn.phones.services.ClientService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/client")
public class ClientController {

  private ClientService clientService;

  @Autowired
  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  @PostMapping("/")
  public ResponseEntity<Client> save(@RequestBody Client client) {
    this.clientService.save(client);
    return ResponseEntity.created(getLocation(client)).build();
  }

  @GetMapping("/")
  public ResponseEntity<List<Client>> findAll() {
    return ResponseEntity.ok(this.clientService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Client> findById(@PathVariable Integer id) throws Exception {
    return ResponseEntity.ok(this.clientService.findById(id));
  }

  private URI getLocation(Client client) {
    return ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(client.getId())
        .toUri();
  }

}
