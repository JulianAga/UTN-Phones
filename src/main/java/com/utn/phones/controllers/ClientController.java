package com.utn.phones.controllers;

import com.utn.phones.dto.UserRequestDto;
import com.utn.phones.exceptions.cityExceptions.CityNotFoundException;
import com.utn.phones.exceptions.clientExceptions.ClientNotFoundException;
import com.utn.phones.model.Client;
import com.utn.phones.services.ClientService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class ClientController {

  private ClientService clientService;

  @Autowired
  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  public URI save(@RequestBody UserRequestDto client) throws CityNotFoundException {
    return getLocation(this.clientService.saveDto(client));
  }

  public ResponseEntity<List<Client>> findAll() {
    return ResponseEntity.ok(this.clientService.findAll());
  }

  public ResponseEntity<Client> findById(@PathVariable Integer id) throws Exception {
    return ResponseEntity.ok(this.clientService.findById(id));
  }

  public Client update(Integer id, UserRequestDto client)
      throws ClientNotFoundException, CityNotFoundException {
    return this.clientService.update(id,client);
  }

  public void deleteById(Integer id) throws ClientNotFoundException {
    this.clientService.deleteById(id);
  }

  private URI getLocation(Client client) {
    return ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(client.getId())
        .toUri();
  }

}
