package com.utn.phones.controllers;

import com.utn.phones.dto.OriginCityAndDestinyCityDto;
import com.utn.phones.dto.PhoneLineDto;
import com.utn.phones.exceptions.cityExceptions.CityNotFoundException;
import com.utn.phones.exceptions.phoneLinesExceptions.PhoneLineAlreadyExists;
import com.utn.phones.exceptions.phoneLinesExceptions.PhoneLineNotExists;
import com.utn.phones.model.*;
import com.utn.phones.services.PhoneLineService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.platform.commons.util.ClassLoaderUtils.getLocation;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class PhoneLineControllerTest {

    private PhoneLineService phoneLineService;
    private PhoneLineController phoneLineController;

    @Before
    public void setUp() {
        phoneLineService = mock(PhoneLineService.class);
        phoneLineController = new PhoneLineController(phoneLineService);
    }

    @Test
    public void testFindAll() {

        PhoneLine phoneLineTest= new PhoneLine(1, "123", new LineType(), new Client(), false, true);
        PhoneLine phoneLineTest2= new PhoneLine(2, "456", new LineType(), new Client(), false, true);

        List<PhoneLine> phoneLineListTest = Arrays.asList(phoneLineTest, phoneLineTest2);
        when(this.phoneLineService.findAll()).thenReturn(phoneLineListTest);

        List<PhoneLine> phoneLineListTest2 = this.phoneLineController.findAll();

        Assert.assertEquals(phoneLineListTest.size(), phoneLineListTest2.size());
        Assert.assertEquals(phoneLineListTest.get(0).getId(), phoneLineListTest2.get(0).getId());
    }

    @Test
    public void saveTest() throws PhoneLineAlreadyExists {
        PhoneLine testPhoneLine = new PhoneLine(1, "123", new LineType(), new Client(), false, true);
        City testCity= new City();

        when(phoneLineService.save(testPhoneLine, testCity)).thenReturn(testPhoneLine);

        PhoneLine phoneLineTest = phoneLineController.save(testPhoneLine, testCity);
        Assert.assertEquals(phoneLineTest.getId(), phoneLineController.save(testPhoneLine, testCity).getId());
    }


    @Test(expected = PhoneLineAlreadyExists.class)
    public void testSaveWithException() throws PhoneLineAlreadyExists {
        PhoneLine testPhoneLine = new PhoneLine(1, "123", new LineType(), new Client(), false, true);
        City testCity= new City();

        when(phoneLineService.save(testPhoneLine, testCity)).thenThrow(new PhoneLineAlreadyExists());
        phoneLineController.save(testPhoneLine, testCity);
    }


    @Test
    public void updateTest()
            throws PhoneLineNotExists {
        PhoneLineDto phoneLineDtoTest= new PhoneLineDto(false, "123");
        PhoneLine phoneLineTest= new PhoneLine(1, "123", new LineType(), new Client(), false, true);
        City testCity= new City();

        when(phoneLineService.update(phoneLineDtoTest, testCity, 1)).thenReturn(phoneLineTest);

        PhoneLine pl = phoneLineController.update(phoneLineDtoTest, testCity, 1);
        assertEquals(phoneLineTest.getId(), pl.getId());
        assertEquals(phoneLineTest.getNumber().length(), pl.getNumber().length());

        verify(phoneLineService, times(1)).update(phoneLineDtoTest, testCity, 1);
    }

    @Test(expected = PhoneLineNotExists.class)
    public void testUpdateWithException() throws PhoneLineNotExists {
        PhoneLineDto phoneLineDtoTest= new PhoneLineDto(false, "123");
        City testCity= new City();

        when(phoneLineService.update(phoneLineDtoTest, testCity, 1)).thenThrow(new PhoneLineNotExists());
        phoneLineController.update(phoneLineDtoTest, testCity, 1);
    }

    @Test
    public void deleteByIdTest() throws PhoneLineNotExists {
        doNothing().when(phoneLineService).deleteById(1);
        phoneLineController.deleteById(1);
        verify(phoneLineService, times(1)).deleteById(1);
    }

}
