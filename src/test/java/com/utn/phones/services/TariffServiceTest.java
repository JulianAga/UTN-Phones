package com.utn.phones.services;

import com.utn.phones.dto.OriginCityAndDestinyCityDto;
import com.utn.phones.exceptions.cityExceptions.CityNotFoundException;
import com.utn.phones.exceptions.generalExceptions.ResourceNotFoundException;
import com.utn.phones.model.*;
import com.utn.phones.repositories.PhoneLineRepository;
import com.utn.phones.repositories.TariffRepository;

import java.util.Arrays;
import java.util.List;

import java.util.Optional;
import org.junit.Assert;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

import com.utn.phones.exceptions.loginExceptions.UserNotExistException;
import com.utn.phones.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

//import org.junit.Before;

public class TariffServiceTest {

    TariffService tariffService;
    TariffRepository tariffRepository;

    @Before
    public void setUp() {
        tariffRepository = mock(TariffRepository.class);
        tariffService = new TariffService(tariffRepository);
    }

    @Test
    public void testFindAll() throws CityNotFoundException{
        City c1= new City();
        City c2= new City();
        Tariff tariffTest = new Tariff(1, c1, c2, 0.25f, 0.5f);
        Tariff tariffTest2 = new Tariff(2, c1, c2, 0.5f, 0.75f);
        OriginCityAndDestinyCityDto dtoTest= new OriginCityAndDestinyCityDto(c1.getName(),c2.getName());

        List<Tariff> tariffList = Arrays.asList(tariffTest,tariffTest2);
        when(this.tariffRepository.findAll()).thenReturn(tariffList);

        List<Tariff> tariffList2 = this.tariffService.findAll(dtoTest.getOriginCity(), dtoTest.getDestinyCity());

        Assert.assertEquals(tariffList.size(), tariffList2.size());
        Assert.assertEquals(tariffList.get(0).getId(), tariffList2.get(0).getId());
    }

    @Test(expected = CityNotFoundException.class)
    public void testFindAllWithException() throws CityNotFoundException {
        when(this.tariffRepository.findByOriginCityNameAndDestinyCityName("", "")).thenReturn(null);

        this.tariffService.findAll("","");
    }

}