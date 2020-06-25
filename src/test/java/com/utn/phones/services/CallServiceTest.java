package com.utn.phones.services;

import com.utn.phones.dto.BetweenDatesDto;
import com.utn.phones.dto.CallRequestDto;
import com.utn.phones.dto.MostCalledDto;
import com.utn.phones.exceptions.callExceptions.CallNotFoundException;
import com.utn.phones.exceptions.clientExceptions.ClientNotFoundException;
import com.utn.phones.exceptions.dateExceptions.InvalidDateException;
import com.utn.phones.model.Call;
import com.utn.phones.repositories.CallRepository;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CallServiceTest {
    @Mock
    private CallRepository callRepository;

    private CallService callService;

    @Before
    public void setUp() {
        initMocks(this);
        callService = new CallService(callRepository);
    }

    @Test
    public void testFindAll(){
        Call call = Call.builder().id(1).build();
        List<Call> calls = Collections.singletonList(call);
        when(callRepository.findAll()).thenReturn(calls);
        List<Call> callList = callService.findAll();
        assertEquals(callList.get(0).getId(),calls.get(0).getId());
    }

    @Test
    public void testFindMostCalledCities() throws CallNotFoundException {

        List<MostCalledDto> mostCalledDtos = new ArrayList<>();
        MostCalledDto mostCalledDto = new MostCalledDto("test1", 3);
        mostCalledDtos.add(mostCalledDto);

        when(callService.findMostCalledCities(1)).thenReturn(mostCalledDtos);
        try {
            List<MostCalledDto> mostCalledDtos2 = callService.findMostCalledCities(1);

            assertEquals(1, mostCalledDtos2.size());
            assertEquals(mostCalledDto, mostCalledDtos.get(0));
            verify(callRepository, times(1)).findMostCalledCities(1);
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

        when(callRepository.findAllByOriginLineClientIdAndDateBetween(1, betweenDatesDto.getStart(), betweenDatesDto.getEnd())).thenReturn(betweenDates);
        try {
            List<Call> betweenDatesCalls = callService.findBetweenDates( 1, betweenDatesDto);

            assertEquals(1, betweenDatesCalls.size());
            assertEquals(testCall, betweenDatesCalls.get(0));
            verify(callRepository, times(1)).findAllByOriginLineClientIdAndDateBetween(1, betweenDatesDto.getStart(), betweenDatesDto.getEnd());
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

        when(callRepository.findAllByOriginLineClientId(1)).thenReturn(testsCalls);
        try {
            List<Call> testsCallsReturn = callService.findCallsFromClient(1);

            assertEquals(2, testsCalls.size());
            assertEquals(testsCallsReturn.get(1), testsCalls.get(1));
            verify(callRepository, times(1)).findAllByOriginLineClientId(1);
        } catch (ClientNotFoundException ex) {
            fail("client not found");
        }
    }

    @Test
    public void saveTest(){
        CallRequestDto callRequestDto = new CallRequestDto("", "", 12, LocalDate.now());
        Call call = Call.builder().bill(null).costPrice(null).date(callRequestDto.getDate())
                .destinyCity(null).destinyLine(null).destinyNumber(callRequestDto.getDestinyNumber()).duration(callRequestDto.getDuration())
                .originCity(null).originLine(null).originNumber(callRequestDto.getOriginNumber()).totalPrice(null).build();
        when(callRepository.save(call)).thenReturn(call);

        Call testCall = callService.saveDto(callRequestDto);

        assertEquals(call.getId(), testCall.getId());
        verify(callRepository, times(1)).save(call);
    }


    @Test(expected = Exception.class)
    public void findMostCalledNotClientFoundTest() throws CallNotFoundException {
        when(callRepository.findMostCalledCities(1)).thenThrow(new CallNotFoundException());
        List<MostCalledDto> mostCalledDtos = callService.findMostCalledCities(1);
    }

    @Test(expected = Exception.class)
    public void findCallsFromClientNotFoundTest()
            throws ClientNotFoundException {
        when(callRepository.findAllByOriginLineClientId(1)).thenThrow(new ClientNotFoundException());
        List<Call> callList= callService.findCallsFromClient(1);
    }

    @Test(expected = Exception.class)
    public void findBetweenDatesInvalidDateTest()
            throws  InvalidDateException {
        BetweenDatesDto betweenDatesDto = new BetweenDatesDto(LocalDate.now(),LocalDate.now());
        when(callRepository.findAllByOriginLineClientIdAndDateBetween(1, betweenDatesDto.getStart(), betweenDatesDto.getEnd())).thenThrow(new InvalidDateException());
        List<Call> callList = callService.findBetweenDates(1, betweenDatesDto);
    }



}
