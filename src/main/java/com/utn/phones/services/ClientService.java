package com.utn.phones.services;

import com.utn.phones.dto.UserRequestDto;
import com.utn.phones.exceptions.cityExceptions.CityNotFoundException;
import com.utn.phones.exceptions.clientExceptions.ClientNotFoundException;
import com.utn.phones.exceptions.generalExceptions.ResourceAlreadyExistException;
import com.utn.phones.model.Client;
import com.utn.phones.repositories.ClientRepository;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

  public Client saveDto(UserRequestDto clientDto)
      throws ResourceAlreadyExistException, CityNotFoundException {
    Client client = new Client();

    client.setCity(cityService.findById(clientDto.getCity()));
    try {
      client.setUsername(clientDto.getUsername());
      client.setDNI(clientDto.getDni());
      client.setName(clientDto.getName());
      client.setSurname(clientDto.getSurname());
      client.setPassword(hashPassword(clientDto.getPassword()));
      return this.clientRepository.save(client);
    } catch (Exception e) {
      throw new ResourceAlreadyExistException();
    }

  }

  public List<Client> findAll() {
    return this.clientRepository.findAll().stream()
        .filter(client -> client.getActive() == Boolean.TRUE)
        .collect(Collectors.toList());
  }

  public Client findById(Integer id) throws ClientNotFoundException {
    return this.clientRepository.findById(id).orElseThrow(
        ClientNotFoundException::new);
  }

  public Client update(Integer id, UserRequestDto userRequestDto)
      throws ResourceAlreadyExistException, CityNotFoundException, ClientNotFoundException {
    Client client = this.findById(id);
    client.setCity(cityService.findById(userRequestDto.getCity()));
    try {
      client.setSurname(userRequestDto.getSurname());
      client.setPassword(hashPassword(userRequestDto.getPassword()));
      client.setName(userRequestDto.getName());
      client.setDNI(userRequestDto.getDni());
      client.setUsername(userRequestDto.getUsername());
      return this.clientRepository.save(client);
    } catch (Exception e) {
      throw new ResourceAlreadyExistException();
    }
  }

  public void deleteById(Integer id) throws ClientNotFoundException {
    Client client = this.findById(id);
    client.setActive(Boolean.FALSE);
    this.clientRepository.save(client);
  }

  private String hashPassword(String password) throws NoSuchAlgorithmException {
    MessageDigest m = MessageDigest.getInstance("MD5");
    byte[] data = password.getBytes();
    m.update(data, 0, data.length);
    BigInteger i = new BigInteger(1, m.digest());
    System.out.println(String.format("%1$032X", i));
    return String.format("%1$032X", i);
  }
}
