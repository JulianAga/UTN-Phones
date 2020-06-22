package com.utn.phones.services;

import com.utn.phones.dto.PhoneLineDto;
import com.utn.phones.exceptions.generalExceptions.ResourceNotFoundException;
import com.utn.phones.exceptions.phoneLinesExceptions.PhoneLineAlreadyExists;
import com.utn.phones.exceptions.phoneLinesExceptions.PhoneLineNotExists;
import com.utn.phones.model.*;
import com.utn.phones.repositories.PhoneLineRepository;
import com.utn.phones.services.PhoneLineService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.jdbc.Sql;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PhoneLineServiceTest {

  private PhoneLineService phoneLineService;
  private PhoneLineRepository phoneLineRepository;

  @Before
  public void setUp() {
    phoneLineRepository= mock(PhoneLineRepository.class);
    phoneLineService = new PhoneLineService(phoneLineRepository);
  }

  @Test
  public void testFindAll() {

    PhoneLine phoneLineTest= new PhoneLine(1, "123", new LineType(), new Client(), false, true);
    PhoneLine phoneLineTest2= new PhoneLine(2, "456", new LineType(), new Client(), false, true);

    List<PhoneLine> phoneLineListTest = Arrays.asList(phoneLineTest, phoneLineTest2);
    when(this.phoneLineRepository.findAll()).thenReturn(phoneLineListTest);

    List<PhoneLine> phoneLineListTest2 = this.phoneLineService.findAll();

    Assert.assertEquals(phoneLineListTest.size(), phoneLineListTest2.size());
    Assert.assertEquals(phoneLineListTest.get(0).getId(), phoneLineListTest2.get(0).getId());
  }

  @Test
  public void saveTest() throws PhoneLineAlreadyExists {
    PhoneLine testPhoneLine = new PhoneLine(1, "123", new LineType(), new Client(), false, true);
    City testCity= new City();

    when(phoneLineRepository.save(testPhoneLine)).thenReturn(testPhoneLine);

    PhoneLine phoneLineTest = phoneLineService.save(testPhoneLine, testCity);
    Assert.assertEquals(phoneLineTest.getId(), phoneLineService.save(testPhoneLine, testCity).getId());
  }

  @Test(expected = Exception.class)
  public void saveTestWithError() throws PhoneLineAlreadyExists {
    PhoneLine testPhoneLine = new PhoneLine(1, "123", new LineType(), new Client(), false, true);
    City testCity= new City();

    when(phoneLineRepository.save(testPhoneLine)).thenThrow(new PhoneLineAlreadyExists());
    phoneLineService.save(testPhoneLine, testCity);
  }

  @Test
  public void testFindById() throws PhoneLineNotExists{
    PhoneLine phoneLineTest= new PhoneLine(1, "123", new LineType(), new Client(), false, true);
    when(this.phoneLineRepository.findById(1)).thenReturn(java.util.Optional.of(phoneLineTest));
    Assert.assertEquals(phoneLineService.findById(1).getId(), phoneLineTest.getId());
  }

  @Test(expected = PhoneLineNotExists.class)
  public void testFindByIdWithErrors() throws PhoneLineNotExists {
    when(phoneLineRepository.findById(1)).thenReturn(Optional.ofNullable(null));
    phoneLineService.findById(1);
  }

  @Test
  public void updateTest()
          throws PhoneLineNotExists {
    PhoneLineDto phoneLineDtoTest= new PhoneLineDto(false, "123");
    PhoneLine phoneLineTest= new PhoneLine(1, "123", new LineType(), new Client(), false, true);
    City testCity= new City();

    when(this.phoneLineRepository.findById(1)).thenReturn(java.util.Optional.of(phoneLineTest));
    when(phoneLineRepository.save(phoneLineTest)).thenReturn(phoneLineTest);
    this.phoneLineService.update(phoneLineDtoTest, testCity, 1);
    verify(phoneLineRepository, times(1)).save(phoneLineTest);
  }


  @Test(expected = Exception.class)
  public void testUpdateWithException() throws PhoneLineNotExists {
    PhoneLine phoneLineTest= new PhoneLine(1, "123", new LineType(), new Client(), false, true);
    PhoneLineDto phoneLineDtoTest= new PhoneLineDto(false, "123");
    City testCity= new City();

    when(this.phoneLineRepository.findById(1)).thenReturn(java.util.Optional.of(phoneLineTest));
    when(phoneLineRepository.save(phoneLineTest)).thenReturn(phoneLineTest);
    when(phoneLineService.update(phoneLineDtoTest, testCity, 1)).thenThrow(PhoneLineNotExists.class);
  }


}
