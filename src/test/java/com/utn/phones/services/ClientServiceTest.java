package com.utn.phones.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.utn.phones.dto.UserRequestDto;
import com.utn.phones.exceptions.cityExceptions.CityNotFoundException;
import com.utn.phones.exceptions.clientExceptions.ClientNotFoundException;
import com.utn.phones.exceptions.generalExceptions.ResourceAlreadyExistException;
import com.utn.phones.model.City;
import com.utn.phones.model.Client;
import com.utn.phones.model.PhoneLine;
import com.utn.phones.model.UserType;
import com.utn.phones.repositories.ClientRepository;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class ClientServiceTest {

  ClientService clientService;
  @Mock
  CityService cityService;
  @Mock
  ClientRepository clientRepository;

  @Before
  public void setUp() {
    initMocks(this);
    clientService = new ClientService(clientRepository, cityService);
  }

  @Test
  public void testFindAll() throws CityNotFoundException {
    List<PhoneLine> pl = new ArrayList<PhoneLine>();
    Client c1 = new Client(1, "123", "hola", "chau", "pepe", "perez", new City(), new UserType(),
        true, pl);
    Client c2 = new Client(2, "456", "hola", "chau", "pepe", "perez", new City(), new UserType(),
        true, pl);

    List<Client> clientList = Arrays.asList(c1, c2);
    when(this.clientRepository.findAll()).thenReturn(clientList);

    List<Client> clientList2 = this.clientService.findAll();

    Assert.assertEquals(clientList.size(), clientList2.size());
    Assert.assertEquals(clientList.get(0).getId(), clientList2.get(0).getId());
  }

  @Test
  public void saveTest() throws CityNotFoundException, ResourceAlreadyExistException {
    Client testClient = new Client(1, "123", "foo", "foo", "foo", "foo", new City(),
        new UserType(), true, null);
    UserRequestDto userRequestDto = new UserRequestDto("123", "foo", "foo", "foo", "foo", 1);

    when(clientRepository.save(testClient)).thenReturn(testClient);
    when(cityService.findById(userRequestDto.getCity())).thenReturn(new City());
    Client client = clientService.saveDto(userRequestDto);

    assertEquals(client.getDNI(), testClient.getDNI());
  }

  @Test
  public void findByIdTest() throws ClientNotFoundException {

    Client testClient = new Client(1, "123", "foo", "foo", "foo", "foo", new City(),
        new UserType(), true, null);

    when(clientRepository.findById(1)).thenReturn(java.util.Optional.of(testClient));
    Client client = clientService.findById(1);
    assertEquals(testClient.getId(), client.getId());
    verify(clientRepository, times(1)).findById(1);
  }

  @Test
  public void deleteByIdTest() throws ClientNotFoundException {

    Client testClient = new Client(1, "123", "foo", "foo", "foo", "foo", new City(),
        new UserType(), true, null);

    when(clientRepository.findById(1)).thenReturn(java.util.Optional.of(testClient));
    clientService.deleteById(1);
    verify(clientRepository, times(1)).findById(1);
  }

  @Test
  public void updateTest()
      throws ClientNotFoundException, CityNotFoundException, ResourceAlreadyExistException, NoSuchAlgorithmException {
    UserRequestDto userRequestDto =
        new UserRequestDto("123", "foo", "foo", "foo", "foo", 1);
    City city = City.builder().name("testCity").id(1).build();
    Client testClient = new Client(1, "123", "foo", "foo", "foo", "foo", city,
        new UserType(), true, null);
    Integer id = 1;

    when(cityService.findById(userRequestDto.getCity())).thenReturn(city);
    when(clientRepository.findById(id)).thenReturn(Optional.ofNullable(testClient));

    when(clientRepository.save(testClient)).thenReturn(testClient);

    Client clientUpdate = clientService.update(id, userRequestDto);

    verify(clientRepository, times(1)).save(testClient);

  }

  @Test(expected = ClientNotFoundException.class)
  public void deleteClientNotFound() throws ClientNotFoundException {
    Integer id = 0;
    when(clientRepository.findById(id)).thenReturn(Optional.ofNullable(null));
    clientService.deleteById(id);
  }


}
