package com.utn.phones.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.utn.phones.projections.ClientsMoreThanThree;
import com.utn.phones.services.ClientService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

public class ClientControllerTest {

  @Autowired
  @InjectMocks
  ClientController clientController;

  @Mock
  ClientService clientService;

  @Before
  public void setUp() {
    initMocks(this);
  }

  @Test
  public void getClientsMoreThanThreeCallsOk() {

    ClientsMoreThanThree client = null;
    ClientsMoreThanThree client2 = null;
    ClientsMoreThanThree client3 = null;
    List<ClientsMoreThanThree> clients = new ArrayList<>();

    clients.add(client);
    clients.add(client2);
    clients.add(client3);

    when(clientService.getClientsMoreThanThreeCalls()).thenReturn(clients);

    List<ClientsMoreThanThree> clientsRec = (List<ClientsMoreThanThree>) clientController.getClientsMoreThanThreeCalls();

    assertEquals(clients,clientsRec);
  }

}
