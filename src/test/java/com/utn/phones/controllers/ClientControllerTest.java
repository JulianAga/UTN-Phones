package com.utn.phones.controllers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        new UserType(), true,null);

    List<Client> clientListTest = Arrays.asList(client, client2);
    when(this.clientService.findAll()).thenReturn(clientListTest);

    List<Client> clientListTest2 = this.clientController.findAll();

    Assert.assertEquals(clientListTest.size(), clientListTest2.size());
    Assert.assertEquals(clientListTest.get(0).getId(), clientListTest2.get(0).getId());
  }

  @Test
  public void testSave(){

  }
}
