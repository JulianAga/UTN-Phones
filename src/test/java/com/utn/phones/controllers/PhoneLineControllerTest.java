package com.utn.phones.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.utn.phones.dto.PhoneLineDto;
import com.utn.phones.exceptions.clientExceptions.ClientNotFoundException;
import com.utn.phones.exceptions.phoneLinesExceptions.PhoneLineAlreadyExists;
import com.utn.phones.exceptions.phoneLinesExceptions.PhoneLineNotExists;
import com.utn.phones.model.City;
import com.utn.phones.model.Client;
import com.utn.phones.model.LineType;
import com.utn.phones.model.PhoneLine;
import com.utn.phones.services.PhoneLineService;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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

    PhoneLine phoneLineTest = new PhoneLine(1, "123", new LineType(), new Client(), false, true);
    PhoneLine phoneLineTest2 = new PhoneLine(2, "456", new LineType(), new Client(), false, true);

    List<PhoneLine> phoneLineListTest = Arrays.asList(phoneLineTest, phoneLineTest2);
    when(this.phoneLineService.findAll()).thenReturn(phoneLineListTest);

    List<PhoneLine> phoneLineListTest2 = this.phoneLineController.findAll();

    Assert.assertEquals(phoneLineListTest.size(), phoneLineListTest2.size());
    Assert.assertEquals(phoneLineListTest.get(0).getId(), phoneLineListTest2.get(0).getId());
  }

  @Test
  public void saveTest() throws PhoneLineAlreadyExists, ClientNotFoundException {
    PhoneLine testPhoneLine = new PhoneLine(1, "123", new LineType(), new Client(), false, true);
    PhoneLineDto phoneLineDto = PhoneLineDto.builder().number("").build();
    when(phoneLineService.save(phoneLineDto, 1)).thenReturn(testPhoneLine);

    PhoneLine phoneLineTest = phoneLineController.save(phoneLineDto, 1);
    Assert.assertEquals(phoneLineTest.getId(), phoneLineController.save(phoneLineDto, 1).getId());
  }


  @Test(expected = PhoneLineAlreadyExists.class)
  public void testSaveWithException() throws PhoneLineAlreadyExists, ClientNotFoundException {
    PhoneLine testPhoneLine = new PhoneLine(1, "123", new LineType(), new Client(), false, true);
    PhoneLineDto phoneLineDto = PhoneLineDto.builder().number("").build();

    when(phoneLineService.save(phoneLineDto, 1)).thenThrow(new PhoneLineAlreadyExists());
    phoneLineController.save(phoneLineDto, 1);
  }


  @Test
  public void updateTest()
      throws PhoneLineNotExists {
    PhoneLineDto phoneLineDtoTest = PhoneLineDto.builder().number("123").build();
    PhoneLine phoneLineTest = new PhoneLine(1, "123", new LineType(), new Client(), false, true);
    City testCity = new City();

    when(phoneLineService.update(phoneLineDtoTest, 1)).thenReturn(phoneLineTest);

    PhoneLine pl = phoneLineController.update(phoneLineDtoTest, 1);
    assertEquals(phoneLineTest.getId(), pl.getId());
    assertEquals(phoneLineTest.getNumber().length(), pl.getNumber().length());

    verify(phoneLineService, times(1)).update(phoneLineDtoTest, 1);
  }

  @Test(expected = PhoneLineNotExists.class)
  public void testUpdateWithException() throws PhoneLineNotExists {
    PhoneLineDto phoneLineDtoTest = PhoneLineDto.builder().number("123").build();

    when(phoneLineService.update(phoneLineDtoTest, 1))
        .thenThrow(new PhoneLineNotExists());
    phoneLineController.update(phoneLineDtoTest,  1);
  }

  @Test
  public void deleteByIdTest() throws PhoneLineNotExists {
    doNothing().when(phoneLineService).deleteById(1);
    phoneLineController.deleteById(1);
    verify(phoneLineService, times(1)).deleteById(1);
  }

}
