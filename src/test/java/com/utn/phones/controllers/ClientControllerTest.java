package com.utn.phones.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.utn.phones.dto.UserRequestDto;
import com.utn.phones.exceptions.cityExceptions.CityNotFoundException;
import com.utn.phones.exceptions.clientExceptions.ClientNotFoundException;
import com.utn.phones.exceptions.generalExceptions.ResourceAlreadyExistException;
import com.utn.phones.model.City;
import com.utn.phones.model.Client;
import com.utn.phones.model.UserType;
import com.utn.phones.services.ClientService;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ClientControllerTest {

  private ClientService clientService;
  private ClientController clientController;

  @Before
  public void setUp() {
    clientService = mock(ClientService.class);
    clientController = new ClientController(clientService);
  }

  @Test
  public void testFindAll() {

    Client client = new Client(1, "dwad", "sad", "ad", "dad", "sad", new City(),
        null, null, null);
    Client client2 = new Client(2, "carlos", "asd", "asd", "sad", "sad", new City(),
        new UserType(), true, null);

    List<Client> clientListTest = Arrays.asList(client, client2);
    when(this.clientService.findAll()).thenReturn(clientListTest);

    List<Client> clientListTest2 = this.clientController.findAll();

    Assert.assertEquals(clientListTest.size(), clientListTest2.size());
    Assert.assertEquals(clientListTest.get(0).getId(), clientListTest2.get(0).getId());
  }

  @Test
  public void saveTest() throws CityNotFoundException, ResourceAlreadyExistException {
    Client testClient = new Client(1, "123", "foo", "foo", "foo", "foo", new City(),
        new UserType(), true, null);
    UserRequestDto userRequestDto = new UserRequestDto("123", "foo", "foo", "foo", "foo", 1);

    when(clientService.saveDto(userRequestDto)).thenReturn(testClient);

    Client client = clientController.save(userRequestDto);

    assertEquals(client.getId(), testClient.getId());

    verify(clientService, times(1)).saveDto(userRequestDto);
  }

  @Test
  public void findByIdTest() throws ClientNotFoundException {

    Client testClient = new Client(1, "123", "foo", "foo", "foo", "foo", new City(),
        new UserType(), true, null);

    when(clientService.findById(1)).thenReturn(testClient);
    Client client = clientController.findById(1);
    assertEquals(testClient.getId(), client.getId());
    verify(clientService, times(1)).findById(1);
  }

  @Test
  public void updateTest()
      throws ClientNotFoundException, CityNotFoundException, ResourceAlreadyExistException {
    UserRequestDto userRequestDto = new UserRequestDto("123", "foo", "foo", "foo", "foo", 1);
    ;
    Client testClient = new Client(1, "123", "foo", "foo", "foo", "foo", new City(),
        new UserType(), true, null);

    when(clientService.update(1, userRequestDto)).thenReturn(testClient);

    Client client = clientController.update(1, userRequestDto);
    assertEquals(testClient.getId(), client.getId());
    assertEquals(testClient.getDNI().length(), client.getDNI().length());

    verify(clientService, times(1)).update(1, userRequestDto);
  }

  @Test
  public void deleteByIdTest() throws ClientNotFoundException {
    doNothing().when(clientService).deleteById(1);
    clientController.deleteById(1);
    verify(clientService, times(1)).deleteById(1);
  }


}
