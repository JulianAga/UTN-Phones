package com.utn.phones.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.utn.phones.dto.PhoneLineDto;
import com.utn.phones.exceptions.clientExceptions.ClientNotFoundException;
import com.utn.phones.exceptions.phoneLinesExceptions.PhoneLineAlreadyExists;
import com.utn.phones.exceptions.phoneLinesExceptions.PhoneLineNotExists;
import com.utn.phones.model.City;
import com.utn.phones.model.Client;
import com.utn.phones.model.LineType;
import com.utn.phones.model.PhoneLine;
import com.utn.phones.model.UserType;
import com.utn.phones.repositories.PhoneLineRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class PhoneLineServiceTest {

  @Mock
  CityService cityService;
  @Mock
  private ClientService clientService;

  private PhoneLineService phoneLineService;
  private PhoneLineRepository phoneLineRepository;

  @Before
  public void setUp() {
    initMocks(this);
    phoneLineRepository = mock(PhoneLineRepository.class);
    clientService = mock(ClientService.class);
    phoneLineService = new PhoneLineService(phoneLineRepository, cityService, clientService);
  }

  @Test
  public void testFindAll() {

    PhoneLine phoneLineTest = new PhoneLine(1, "123", new LineType(), new Client(), false, true);
    PhoneLine phoneLineTest2 = new PhoneLine(2, "456", new LineType(), new Client(), false, true);

    List<PhoneLine> phoneLineListTest = Arrays.asList(phoneLineTest, phoneLineTest2);
    when(this.phoneLineRepository.findAll()).thenReturn(phoneLineListTest);

    List<PhoneLine> phoneLineListTest2 = this.phoneLineService.findAll();

    Assert.assertEquals(phoneLineListTest.size(), phoneLineListTest2.size());
    Assert.assertEquals(phoneLineListTest.get(0).getId(), phoneLineListTest2.get(0).getId());
  }

  @Test
  public void saveTest() throws PhoneLineAlreadyExists, ClientNotFoundException {
    Client client = new Client(1, "123", "foo",
        "foo", "foo", "foo", new City(),
        UserType.builder().id(1).build(), true, new ArrayList<>());
    PhoneLine testPhoneLine = new PhoneLine(1, "123", new LineType(),
        client, false, true);
    PhoneLineDto phoneLineDto = PhoneLineDto.builder().number("123").build();

    when(phoneLineRepository.save(testPhoneLine)).thenReturn(testPhoneLine);
    when(clientService.findById(testPhoneLine.getClient().getId())).thenReturn(client);
    PhoneLine phoneLine = phoneLineService.save(phoneLineDto, 1);

    assertEquals(testPhoneLine.getNumber(), phoneLineDto.getNumber());

  }

  @Test(expected = Exception.class)
  public void saveTestWithError() throws PhoneLineAlreadyExists, ClientNotFoundException {
    PhoneLine testPhoneLine = new PhoneLine(1, "123", new LineType(), new Client(), false, true);
    PhoneLineDto phoneLineDto = PhoneLineDto.builder().number("").build();

    when(phoneLineRepository.save(testPhoneLine)).thenThrow(new PhoneLineAlreadyExists());
    phoneLineService.save(phoneLineDto, 1);
  }

  @Test
  public void testFindById() throws PhoneLineNotExists {
    PhoneLine phoneLineTest = new PhoneLine(1, "123", new LineType(), new Client(), false, true);
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
    PhoneLineDto phoneLineDto = PhoneLineDto.builder().number("").build();
    PhoneLine phoneLineTest = new PhoneLine(1, "123", new LineType(), new Client(), false, true);

    when(this.phoneLineRepository.findById(1)).thenReturn(java.util.Optional.of(phoneLineTest));
    when(phoneLineRepository.save(phoneLineTest)).thenReturn(phoneLineTest);
    this.phoneLineService.update(phoneLineDto, 1);
    verify(phoneLineRepository, times(1)).save(phoneLineTest);
  }


  @Test(expected = Exception.class)
  public void testUpdateWithException() throws PhoneLineNotExists {
    PhoneLine phoneLineTest = new PhoneLine(1, "123", new LineType(), new Client(), false, true);
    PhoneLineDto phoneLineDto = PhoneLineDto.builder().number("").build();
    City testCity = new City();

    when(this.phoneLineRepository.findById(1)).thenReturn(java.util.Optional.of(phoneLineTest));
    when(phoneLineRepository.save(phoneLineTest)).thenReturn(phoneLineTest);
    when(phoneLineService.update(phoneLineDto, 1))
        .thenThrow(PhoneLineNotExists.class);
  }

}
