package com.utn.phones.controllers;

import com.utn.phones.dto.UserRequestDto;
import com.utn.phones.exceptions.cityExceptions.CityNotFoundException;
import com.utn.phones.exceptions.clientExceptions.ClientNotFoundException;
import com.utn.phones.exceptions.generalExceptions.ResourceAlreadyExistException;
import com.utn.phones.model.Client;
import com.utn.phones.restUtils.RestUtils;
import com.utn.phones.services.ClientService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  public Client save(@RequestBody UserRequestDto client)
      throws CityNotFoundException, ResourceAlreadyExistException {
    return this.clientService.saveDto(client);
  }

  public List<Client> findAll() {
    return this.clientService.findAll();
  }

  @GetMapping("/id")
  public Client findById(@PathVariable Integer id) throws ClientNotFoundException {
    return this.clientService.findById(id);
  }

  public Client update(Integer id, UserRequestDto client)
      throws ClientNotFoundException, CityNotFoundException, ResourceAlreadyExistException {
    return this.clientService.update(id,client);
  }

  public void deleteById(Integer id) throws ClientNotFoundException {
    this.clientService.deleteById(id);
  }

}
