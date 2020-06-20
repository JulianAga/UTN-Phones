package com.utn.phones.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.utn.phones.dto.BetweenDatesDto;
import com.utn.phones.dto.MostCalledDto;
import com.utn.phones.exceptions.callExceptions.CallNotFoundException;
import com.utn.phones.exceptions.dateExceptions.InvalidDateException;
import com.utn.phones.model.Call;
import com.utn.phones.services.CallService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class CallControllerTest {

  private CallService callService;
  private CallController callController;

  @Before
  public void setUp() {
    callService = mock(CallService.class);
    callController = new CallController(callService);
  }

  @Test
  public void testFindMostCalledCities() throws CallNotFoundException {

    List<MostCalledDto> mostCalledDtos = new ArrayList<>();
    MostCalledDto mostCalledDto = new MostCalledDto("test1", 3);
    mostCalledDtos.add(mostCalledDto);

    when(callService.findMostCalledCities(1)).thenReturn(mostCalledDtos);

    List<MostCalledDto> mostCalledDtos2 = callController.findMostCalledCities(1);

    assertEquals(1, mostCalledDtos2.size());
    assertEquals(mostCalledDto, mostCalledDtos.get(0));
    verify(callService, times(1)).findMostCalledCities(1);
  }

  @Test
  public void testFindBetweenDates() throws CallNotFoundException, InvalidDateException {

    Call testCall = new Call(1, 2, LocalDate.of(2020, 6, 30), 20f, 25f, null, null, null, null, null, null,
        null);

    List<Call> betweenDates = Collections
        .singletonList(testCall);
    LocalDate date1 = LocalDate.of(2020, 5, 30);
    LocalDate date2 = LocalDate.of(2020, 7, 30);
    BetweenDatesDto betweenDatesDto = new BetweenDatesDto(date1, date2);

    when(callService.findBetweenDates(1, betweenDatesDto)).thenReturn(betweenDates);

    List<Call> betweenDatesCalls = callController.findBetweenDates(betweenDatesDto,1);

    assertEquals(1, betweenDatesCalls.size());
    assertEquals(testCall, betweenDatesCalls.get(0));
    verify(callService, times(1)).findBetweenDates(1,betweenDatesDto);
  }


}
