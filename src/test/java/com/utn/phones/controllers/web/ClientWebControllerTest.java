package com.utn.phones.controllers.web;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.utn.phones.Sessions.SessionManager;
import com.utn.phones.controllers.BillController;
import com.utn.phones.controllers.CallController;
import com.utn.phones.controllers.ClientController;
import com.utn.phones.dto.BetweenDatesDto;
import com.utn.phones.projections.MostCalled;
import com.utn.phones.exceptions.callExceptions.CallNotFoundException;
import com.utn.phones.exceptions.clientExceptions.ClientNotFoundException;
import com.utn.phones.exceptions.dateExceptions.InvalidDateException;
import com.utn.phones.exceptions.loginExceptions.UserNotExistException;
import com.utn.phones.model.Bill;
import com.utn.phones.model.Call;
import com.utn.phones.model.City;
import com.utn.phones.model.Client;
import com.utn.phones.model.UserType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ClientWebControllerTest {

  @Mock
  SessionManager sessionManager;

  @Mock
  ClientController clientController;

  @Mock
  BillController billController;

  @Mock
  CallController callController;

  ClientWebController clientWebController;
  private MostCalled mostCalledProjection;
  private List<MostCalled> mostCalledList;

  Client client = new Client();

  @Before
  public void setUp() {
    initMocks(this);
    clientWebController = new ClientWebController(clientController, callController, billController,
        sessionManager);
    client = new Client(1, "123", "foo", "foo", "foo", "foo", new City(),
        UserType.builder().id(2).build(), true, null);
    ProjectionFactory factory= new SpelAwareProxyProjectionFactory();
    this.mostCalledProjection= factory.createProjection(MostCalled.class);
    mostCalledProjection.setCant(2);
    mostCalledProjection.setCityName("Mar del Plata");
    mostCalledList= Collections.singletonList(mostCalledProjection);
  }

  @Test
  public void findCallsFromClientTest()
      throws CallNotFoundException, UserNotExistException {

    String token = sessionManager.createSession(client);
    //devuelvo el cliente cuando se pide el token
    when(sessionManager.getCurrentUser(token)).thenReturn(client);

    when(callController
        .findMostCalledCities(1))
        .thenReturn(mostCalledList);

    ResponseEntity<List<MostCalled>> mostCalledDto = clientWebController
        .findCallFromClient(token);

    assertEquals(mostCalledDto.getBody().size(), mostCalledList.size());
    verify(callController, times(1)).findMostCalledCities(1);
  }

  @Test
  public void findBillBetweenDateTest() throws UserNotExistException, InvalidDateException {

    BetweenDatesDto betweenDatesDto = BetweenDatesDto.builder().start(LocalDate.now())
        .end(LocalDate.now()).build();
    Bill bill = Bill.builder().id(1).build();
    List<Bill> bills = Collections.singletonList(bill);

    String token = sessionManager.createSession(client);
    //devuelvo el cliente cuando se pide el token
    when(sessionManager.getCurrentUser(token)).thenReturn(client);

    when(billController.findBetweenDates(1, betweenDatesDto)).thenReturn(bills);

    ResponseEntity<List<Bill>> billsReturned = clientWebController
        .findBillBetweenDates(token, betweenDatesDto);

    assertEquals(billsReturned.getBody().size(), bills.size());
    assertEquals(billsReturned.getBody().get(0).getId(), bills.get(0).getId());
    verify(billController, times(2)).findBetweenDates(1, betweenDatesDto);
  }

  @Test
  public void findCallBetweenDateTest() throws UserNotExistException, InvalidDateException {

    BetweenDatesDto betweenDatesDto = BetweenDatesDto.builder().start(LocalDate.now())
        .end(LocalDate.now()).build();
    Call call = Call.builder().id(1).build();
    List<Call> calls = Collections.singletonList(call);

    String token = sessionManager.createSession(client);
    //devuelvo el cliente cuando se pide el token
    when(sessionManager.getCurrentUser(token)).thenReturn(client);

    when(callController.findBetweenDates(betweenDatesDto, 1)).thenReturn(calls);

    ResponseEntity<List<Call>> callsReturned = clientWebController
        .findCallBetweenDates(token, betweenDatesDto);

    assertEquals(callsReturned.getBody().size(), calls.size());
    assertEquals(callsReturned.getBody().get(0).getId(), calls.get(0).getId());
    verify(callController, times(2)).findBetweenDates(betweenDatesDto, 1);
  }


  @Test
  public void testFindCallsFromClientNoContent()
      throws ClientNotFoundException, UserNotExistException, CallNotFoundException {
    List<Call> calls = new ArrayList<>();
    when(this.callController.findCallsFromClient(client.getId())).thenReturn(calls);
    when(this.sessionManager.getCurrentUser("token")).thenReturn(client);

    ResponseEntity<?> callsFromClientTest = this.clientWebController.findCallFromClient("token");

    Assert.assertEquals(HttpStatus.NO_CONTENT, callsFromClientTest.getStatusCode());

  }

  @Test
  public void testFindBillBetweenDatesNoContent()
      throws UserNotExistException, CallNotFoundException, InvalidDateException {
    List<Bill> bills = new ArrayList<>();
    BetweenDatesDto betweenDatesDto = BetweenDatesDto.builder().build();
    when(this.billController.findBetweenDates(client.getId(), betweenDatesDto)).thenReturn(bills);
    when(this.sessionManager.getCurrentUser("token")).thenReturn(client);

    ResponseEntity<?> billsFromClientTest = this.clientWebController
        .findBillBetweenDates("token", betweenDatesDto);

    Assert.assertEquals(HttpStatus.NO_CONTENT, billsFromClientTest.getStatusCode());

  }

  @Test
  public void testFindBillsFromClientNoContent()
      throws UserNotExistException, CallNotFoundException, InvalidDateException {
    List<Bill> bills = new ArrayList<>();
    BetweenDatesDto betweenDatesDto = BetweenDatesDto.builder().build();
    when(this.billController.findBetweenDates(client.getId(), betweenDatesDto)).thenReturn(bills);
    when(this.sessionManager.getCurrentUser("token")).thenReturn(client);

    ResponseEntity<?> billsFromClientTest = this.clientWebController
        .findBillBetweenDates("token", betweenDatesDto);

    Assert.assertEquals(HttpStatus.NO_CONTENT, billsFromClientTest.getStatusCode());

  }


}
