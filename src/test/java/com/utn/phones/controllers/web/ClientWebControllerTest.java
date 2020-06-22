package com.utn.phones.controllers.web;

import static org.mockito.Mockito.mock;

import com.utn.phones.Sessions.SessionManager;
import com.utn.phones.controllers.BillController;
import com.utn.phones.controllers.CallController;
import com.utn.phones.controllers.ClientController;
import com.utn.phones.restUtils.RestUtils;
import org.junit.Before;
import org.mockito.Mock;

public class ClientWebControllerTest {

  @Mock
  SessionManager sessionManager;

  @Mock
  RestUtils restUtils;

  @Mock
  ClientController clientController;

  @Mock
  BillController billController;

  @Mock
  CallController callController;

  ClientWebController clientWebController;

  @Before
  public void setUp() {
    clientController = mock(ClientController.class);
    clientWebController = new ClientWebController(clientController,callController,billController,sessionManager);
  }



}
