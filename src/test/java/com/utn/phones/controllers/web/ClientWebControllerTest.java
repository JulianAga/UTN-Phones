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
import com.utn.phones.dto.MostCalledDto;
import com.utn.phones.exceptions.callExceptions.CallNotFoundException;
import com.utn.phones.exceptions.dateExceptions.InvalidDateException;
import com.utn.phones.exceptions.loginExceptions.UserNotExistException;
import com.utn.phones.model.Bill;
import com.utn.phones.model.Call;
import com.utn.phones.model.City;
import com.utn.phones.model.Client;
import com.utn.phones.model.UserType;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
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

  Client client = new Client();

  @Before
  public void setUp() {
    initMocks(this);
    clientWebController = new ClientWebController(clientController, callController, billController,
        sessionManager);
    client = new Client(1, "123", "foo", "foo", "foo", "foo", new City(),
        UserType.builder().id(2).build(), true, null);
  }

  @Test
  public void findCallsFromClientTest()
      throws CallNotFoundException, UserNotExistException {

    MostCalledDto mostCalledDto1 = MostCalledDto.builder().cityName("chivilcoy").cant(123).build();
    MostCalledDto mostCalledDto2 = MostCalledDto.builder().cityName("chascomus").cant(121).build();

    List<MostCalledDto> mostCalledDtos = Arrays.asList(mostCalledDto1, mostCalledDto2);
    String token = sessionManager.createSession(client);
    //devuelvo el cliente cuando se pide el token
    when(sessionManager.getCurrentUser(token)).thenReturn(client);

    when(callController
        .findMostCalledCities(1))
        .thenReturn(mostCalledDtos);

    ResponseEntity<List<MostCalledDto>> mostCalledDto = clientWebController
        .findCallFromClient(token);

    assertEquals(mostCalledDto.getBody().size(), mostCalledDtos.size());
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

}
