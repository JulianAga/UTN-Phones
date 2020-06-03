package com.utn.phones.services;

import com.utn.phones.dto.ClientRequestDto;
import com.utn.phones.model.Client;
import com.utn.phones.projections.ClientsMoreThanThree;
import com.utn.phones.repositories.ClientRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

  private ClientRepository clientRepository;
  private CityService cityService;

  @Autowired
  public ClientService(ClientRepository clientRepository, CityService cityService) {
    this.clientRepository = clientRepository;
    this.cityService = cityService;
  }

  public Client saveDto(ClientRequestDto clientDto) {

    Client client = new Client();
    client.setCity(cityService.findById(clientDto.getCity()));
    client.setUsername(clientDto.getUsername());
    client.setDNI(clientDto.getDni());
    client.setName(clientDto.getName());
    client.setSurname(clientDto.getSurname());
    client.setPassword(clientDto.getPassword());
    return this.clientRepository.save(client);
  }

  public Client save(Client client) {
    return this.clientRepository.save(client);
  }

  public List<Client> findAll() {
    return this.clientRepository.findAll();
  }

  public List<ClientsMoreThanThree> getClientsMoreThanThreeCalls() {
    return this.clientRepository.getClientsMoreThanThreeCalls().stream()
        .filter(clientsMoreThanThree -> clientsMoreThanThree.getTotalCalls() >= 3)
        .collect(
            Collectors.toList());
  }
   /*
  public List<Client> findClientsWithMoreThanThree(){
    return this.clientRepository.findAll().stream().map(
        client -> client.getPhoneLines().stream().filter(
            phoneLine -> phoneLine)
    ).collect(Collectors.toList());
  }
 Parcial 03/06/2020 Forma de hacerlo en codigo*/


  //TODO agregar excepcion user not found
  public Client findById(Integer id) throws Exception {
    return this.clientRepository.findById(id).orElseThrow(Exception::new);
  }
}
