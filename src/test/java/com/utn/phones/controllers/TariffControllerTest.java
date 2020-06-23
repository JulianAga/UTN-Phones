package com.utn.phones.controllers;

import com.utn.phones.dto.OriginCityAndDestinyCityDto;
import com.utn.phones.exceptions.cityExceptions.CityNotFoundException;
import com.utn.phones.model.City;
import com.utn.phones.model.Tariff;
import com.utn.phones.services.TariffService;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TariffControllerTest {

    TariffController tariffController;
    TariffService service;


    @Before
    public void setUp() {
        service = mock(TariffService.class);
        tariffController = new TariffController(service);
    }

    @Test
    public void testFindAll() throws CityNotFoundException {
        Tariff tariffTest = new Tariff(1, new City(), new City(), 0.25f, 0.5f);
        Tariff tariffTest2 = new Tariff(2, new City(), new City(), 0.5f, 0.75f);
        OriginCityAndDestinyCityDto originCityAndDestinyCityDtoTest= new OriginCityAndDestinyCityDto();

        List<Tariff> tariffList = Arrays.asList(tariffTest,tariffTest2);

        when(service.findAll(originCityAndDestinyCityDtoTest.getOriginCity(), originCityAndDestinyCityDtoTest.getDestinyCity())).thenReturn(tariffList);

        List<Tariff> tariffListTest = tariffController.findAll(originCityAndDestinyCityDtoTest);

        assertEquals(tariffList.size(),tariffListTest.size());
        assertEquals(tariffList.get(1).getId(),tariffListTest.get(1).getId());
    }

    @Test(expected = CityNotFoundException.class)
    public void testFindAllNotFound() throws CityNotFoundException {
        OriginCityAndDestinyCityDto originCityAndDestinyCityDtoTest= new OriginCityAndDestinyCityDto();
        when(service.findAll(originCityAndDestinyCityDtoTest.getOriginCity(), originCityAndDestinyCityDtoTest.getDestinyCity())).thenThrow(new CityNotFoundException());
        tariffController.findAll(originCityAndDestinyCityDtoTest);
    }
}