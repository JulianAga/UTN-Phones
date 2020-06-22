package com.utn.phones.services;

import com.utn.phones.dto.BetweenDatesDto;
import com.utn.phones.exceptions.dateExceptions.InvalidDateException;
import com.utn.phones.model.Bill;
import com.utn.phones.model.Client;
import com.utn.phones.model.PhoneLine;
import com.utn.phones.repositories.BillRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class BillServiceTest {


    private BillService billService;

    @Mock
    private BillRepository billRepository;

    @Before
    public void setUp() {
        initMocks(this);
        billService = new BillService(billRepository);
    }


    @Test
    public void testFindBetweenDates() throws InvalidDateException {

        LocalDate l1= LocalDate.of(2020, 6, 1);
        LocalDate l2= LocalDate.of(2020, 7, 30);
        Bill billTest= new Bill(1, new PhoneLine(), new Client(), 1, 0.25f, 0.5f, l1, l2);

        List<Bill> betweenDates = Collections
                .singletonList(billTest);
        LocalDate date1 = LocalDate.of(2020, 5, 30);
        LocalDate date2 = LocalDate.of(2020, 7, 30);
        BetweenDatesDto betweenDatesDto = new BetweenDatesDto(date1, date2);

        when(billRepository.findAllByClientIdAndDateBetween(1, date1, date2)).thenReturn(betweenDates);

        try {
            List<Bill> betweenDatesBills = billService.findBetweenDates(1, betweenDatesDto);
            assertEquals(1, betweenDatesBills.size());
            assertEquals(billTest, betweenDatesBills.get(0));
            verify(billRepository, times(1)).findAllByClientIdAndDateBetween(1, betweenDatesDto.getStart(), betweenDatesDto.getEnd());
        } catch (InvalidDateException ex) {
            fail();
        }
    }

    @Test(expected = Exception.class)
    public void findBetweenDatesInvalidDateTest()
            throws  InvalidDateException {
        BetweenDatesDto betweenDatesDto = new BetweenDatesDto(LocalDate.now(),LocalDate.now());
        when(billRepository.findAllByClientIdAndDateBetween(1, betweenDatesDto.getStart(), betweenDatesDto.getEnd())).thenThrow(new InvalidDateException());
        List<Bill> billList = billService.findBetweenDates(1, betweenDatesDto);
    }


}
