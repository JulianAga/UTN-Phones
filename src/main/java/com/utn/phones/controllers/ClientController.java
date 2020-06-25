package com.utn.phones.controllers;

import com.utn.phones.dto.UserRequestDto;
import com.utn.phones.exceptions.cityExceptions.CityNotFoundException;
import com.utn.phones.exceptions.clientExceptions.ClientNotFoundException;
import com.utn.phones.exceptions.generalExceptions.ResourceAlreadyExistException;
import com.utn.phones.model.Client;
import com.utn.phones.model.PhoneLine;
import com.utn.phones.services.ClientService;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
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

  public Client findById(@PathVariable Integer id) throws ClientNotFoundException {
    Client client = this.clientService.findById(id);
    if (client.getActive().equals(Boolean.TRUE)) {
      //Filtro las lineas activas y no muestro las inactivas
      List<PhoneLine> activePhoneLines = client.getPhoneLines().stream()
          .filter(phoneLine -> phoneLine.getActive().equals(Boolean.TRUE))
          .collect(Collectors.toList());
      client.setPhoneLines(activePhoneLines);

      return client;
    } else {
      throw new ClientNotFoundException();
    }

  }

  public Client update(Integer id, UserRequestDto client)
      throws ClientNotFoundException, CityNotFoundException, ResourceAlreadyExistException, NoSuchAlgorithmException {
    return this.clientService.update(id, client);
  }

  public void deleteById(Integer id) throws ClientNotFoundException {
    this.clientService.deleteById(id);
  }

}
