package com.utn.phones.controllers;

import com.utn.phones.dto.BetweenDatesDto;
import com.utn.phones.dto.CallRequestDto;
import com.utn.phones.projections.MostCalled;
import com.utn.phones.exceptions.callExceptions.CallNotFoundException;
import com.utn.phones.exceptions.clientExceptions.ClientNotFoundException;
import com.utn.phones.exceptions.dateExceptions.InvalidDateException;
import com.utn.phones.model.Call;
import com.utn.phones.services.CallService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CallControllerTest {

  @Mock
  private CallService callService;

  private CallController callController;
  private MostCalled mostCalledProjection;
  private List<MostCalled> mostCalledList;

  @Before
  public void setUp() {
    initMocks(this);
    callController = new CallController(callService);
    ProjectionFactory factory= new SpelAwareProxyProjectionFactory();
    this.mostCalledProjection= factory.createProjection(MostCalled.class);
    mostCalledProjection.setCant(2);
    mostCalledProjection.setCityName("Mar del Plata");
    mostCalledList= Collections.singletonList(mostCalledProjection);
  }

  @Test
  public void testFindMostCalledCities() throws CallNotFoundException {

      when(callService.findMostCalledCities(1)).thenReturn(mostCalledList);
    try {
      List<MostCalled> mostCalledDtos = callController.findMostCalledCities(1);

      assertEquals(1, mostCalledDtos.size());
      assertEquals(mostCalledList.get(0), mostCalledDtos.get(0));
      verify(callService, times(1)).findMostCalledCities(1);
    } catch (CallNotFoundException ex) {
      fail();
    }
  }

  @Test
  public void testFindBetweenDates() throws InvalidDateException {


      Call testCall = new Call(1, 2, LocalDate.of(2020, 6, 30), 20f, 25f, null, null, null, null,
          null, null,
          null);

      List<Call> betweenDates = Collections
          .singletonList(testCall);
      LocalDate date1 = LocalDate.of(2020, 5, 30);
      LocalDate date2 = LocalDate.of(2020, 7, 30);
      BetweenDatesDto betweenDatesDto = new BetweenDatesDto(date1, date2);

      when(callService.findBetweenDates(1, betweenDatesDto)).thenReturn(betweenDates);
    try {
      List<Call> betweenDatesCalls = callController.findBetweenDates(betweenDatesDto, 1);

      assertEquals(1, betweenDatesCalls.size());
      assertEquals(testCall, betweenDatesCalls.get(0));
      verify(callService, times(1)).findBetweenDates(1, betweenDatesDto);
    } catch (InvalidDateException ex) {
      fail();
    }
  }

  @Test
  public void findCallsFromClientTest() throws ClientNotFoundException {

      Call testCall = new Call(1, 2, LocalDate.of(2020, 6, 30), 20f, 25f, null, null, null, null,
          null, null, null);
      Call testCall2 = new Call(1, 2, LocalDate.of(2020, 6, 30), 20f, 25f, null, null, null, null,
          null, null, null);

      List<Call> testsCalls = Arrays.asList(testCall, testCall2);

      when(callService.findCallsFromClient(1)).thenReturn(testsCalls);
    try {
      List<Call> testsCallsReturn = callController.findCallsFromClient(1);

      assertEquals(2, testsCalls.size());
      assertEquals(testsCallsReturn.get(1), testsCalls.get(1));
      verify(callService, times(1)).findCallsFromClient(1);
    } catch (ClientNotFoundException ex) {
      fail("client not found");
    }
  }

  @Test
  public void saveTest() {
    Call testCall = new Call(1, 12, null, null, null, null, null, null, null,
        null, null, null);
    CallRequestDto callRequestDto = new CallRequestDto("", "", 12, LocalDate.now());

    when(callService.saveDto(callRequestDto)).thenReturn(testCall);

    Call call = callController.save(callRequestDto);

    assertEquals(call.getId(), testCall.getId());
    verify(callService, times(1)).saveDto(callRequestDto);
  }


  @Test(expected = CallNotFoundException.class)
  public void findMostCalledNotClientFoundTest() throws CallNotFoundException {
    when(callService.findMostCalledCities(1)).thenThrow(new CallNotFoundException());
    List<MostCalled> mostCalledDtos = callController.findMostCalledCities(1);
  }

  @Test(expected = ClientNotFoundException.class)
  public void findCallsFromClientNotFoundTest()
      throws ClientNotFoundException {
    when(callService.findCallsFromClient(1)).thenThrow(new ClientNotFoundException());
    List<Call> callList= callController.findCallsFromClient(1);
  }

  @Test(expected = InvalidDateException.class)
  public void findBetweenDatesInvalidDateTest()
      throws  InvalidDateException {
    BetweenDatesDto betweenDatesDto = new BetweenDatesDto(LocalDate.now(),LocalDate.now());
    when(callService.findBetweenDates(1,betweenDatesDto)).thenThrow(new InvalidDateException());
    List<Call> callList = callController.findBetweenDates(betweenDatesDto,1);
  }
}
