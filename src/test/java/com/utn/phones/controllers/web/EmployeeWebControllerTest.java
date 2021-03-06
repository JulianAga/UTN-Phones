package com.utn.phones.controllers.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.utn.phones.Sessions.SessionManager;
import com.utn.phones.controllers.BillController;
import com.utn.phones.controllers.CallController;
import com.utn.phones.controllers.ClientController;
import com.utn.phones.controllers.EmployeeController;
import com.utn.phones.controllers.PhoneLineController;
import com.utn.phones.controllers.TariffController;
import com.utn.phones.dto.BetweenDatesDto;
import com.utn.phones.dto.OriginCityAndDestinyCityDto;
import com.utn.phones.dto.PhoneLineDto;
import com.utn.phones.dto.UserRequestDto;
import com.utn.phones.exceptions.cityExceptions.CityNotFoundException;
import com.utn.phones.exceptions.clientExceptions.ClientNotFoundException;
import com.utn.phones.exceptions.dateExceptions.InvalidDateException;
import com.utn.phones.exceptions.generalExceptions.ResourceAlreadyExistException;
import com.utn.phones.exceptions.loginExceptions.UserNotExistException;
import com.utn.phones.exceptions.phoneLinesExceptions.PhoneLineAlreadyExists;
import com.utn.phones.exceptions.phoneLinesExceptions.PhoneLineNotExists;
import com.utn.phones.model.Bill;
import com.utn.phones.model.Call;
import com.utn.phones.model.City;
import com.utn.phones.model.Client;
import com.utn.phones.model.PhoneLine;
import com.utn.phones.model.Tariff;
import com.utn.phones.model.UserType;
import com.utn.phones.restUtils.RestUtils;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RestUtils.class)
public class EmployeeWebControllerTest {

  @Mock
  SessionManager sessionManager;

  @Mock
  RestUtils restUtils;

  @Mock
  EmployeeController employeeController;

  @Mock
  ClientController clientController;

  @Mock
  BillController billController;

  @Mock
  CallController callController;

  @Mock
  TariffController tariffController;

  @Mock
  PhoneLineController phoneLineController;

  EmployeeWebController employeeWebController;

  @Before
  public void setUp() {
    employeeWebController = new EmployeeWebController(tariffController, clientController,
        billController, phoneLineController, callController);
    employeeController = mock(EmployeeController.class);
    PowerMockito.mockStatic(RestUtils.class);
  }

  //**Client management **//

  @Test
  public void AddClientTest() throws ResourceAlreadyExistException, CityNotFoundException {
    Client client = new Client(1, "123", "foo", "foo", "foo", "foo", new City(),
        new UserType(), true, null);

    UserRequestDto userRequestDto = new UserRequestDto("123", "foo", "foo", "foo", "foo", 1);
    when(clientController.save(userRequestDto)).thenReturn(client);
    when(RestUtils.getClientLocation(client)).thenReturn(URI.create("page/1"));

    ResponseEntity<?> returnedUri = employeeWebController.addClient("1", userRequestDto);
    List<String> headers = returnedUri.getHeaders().get("location");
    assert headers != null;
    Assert.assertEquals(headers.get(0), "page/1");
  }

  @Test
  public void deleteClientTest() throws ClientNotFoundException {
    doNothing().when(clientController).deleteById(1);
    employeeWebController.deleteClient("111", 1);
    verify(clientController, times(1)).deleteById(1);
  }

  @Test
  public void updateClientTest()
      throws ClientNotFoundException, CityNotFoundException, ResourceAlreadyExistException, NoSuchAlgorithmException {
    UserRequestDto userRequestDto = new UserRequestDto("123", "foo", "foo", "foo", "foo", 1);
    ;
    Client testClient = new Client(1, "123", "foo", "foo", "foo", "foo", new City(),
        new UserType(), true, null);

    when(clientController.update(1, userRequestDto)).thenReturn(testClient);

    ResponseEntity<Client> client = employeeWebController.updateClient("111", userRequestDto, 1);
    assertEquals(testClient.getId(), client.getBody().getId());
    assertEquals(testClient.getDNI().length(), client.getBody().getDNI().length());

    verify(clientController, times(1)).update(1, userRequestDto);
  }

  public void findClientTest() throws ClientNotFoundException {
    Client testClient = new Client(1, "123", "foo", "foo", "foo", "foo", new City(),
        new UserType(), true, null);

    when(clientController.findById(1)).thenReturn(testClient);
    ResponseEntity<Client> client = employeeWebController.findClient("aaa", 1);
    assertEquals(testClient.getId(), client.getBody().getId());
    verify(clientController, times(1)).findById(1);
  }

  //*** Line management ***//
  @Test
  public void AddPhoneLineTest()
      throws ResourceAlreadyExistException, ClientNotFoundException {
    PhoneLineDto phoneLine = PhoneLineDto.builder().number("123").build();

    PhoneLine phoneLineReturn = PhoneLine.builder().id(1).number("123").build();

    when(phoneLineController.save(phoneLine, 1)).thenReturn(phoneLineReturn);
    when(RestUtils.getPhoneLineLocation(phoneLineReturn)).thenReturn(URI.create("page/1"));

    ResponseEntity<?> returnedUri = employeeWebController.addPhoneLine("1", phoneLine, 1);
    List<String> headers = returnedUri.getHeaders().get("location");
    assert headers != null;
    Assert.assertEquals(headers.get(0), "page/1");
  }

  @Test
  public void updatePhoneLineTest() throws PhoneLineNotExists {
    PhoneLineDto phoneRequestDto = PhoneLineDto.builder().number("0303456").build();
    City city = City.builder().id(1).name("chascomus").build();
    PhoneLine phoneLineTest = PhoneLine.builder().id(1).number("0303456").build();

    when(phoneLineController.update(phoneRequestDto, 1)).thenReturn(phoneLineTest);

    ResponseEntity<PhoneLine> phoneLine = employeeWebController
        .updatePhoneLine("111", phoneRequestDto, 1);
    assertEquals(phoneLine.getBody().getId(), phoneLineTest.getId());
    assertEquals(phoneLine.getBody().getNumber().length(), phoneLineTest.getNumber().length());

    verify(phoneLineController, times(1)).update(phoneRequestDto, 1);
  }

  @Test
  public void deletePhoneLinesTest() throws PhoneLineNotExists, ClientNotFoundException {
    doNothing().when(phoneLineController).deleteById(1);
    employeeWebController.deletePhoneLine("111", 1);
    verify(phoneLineController, times(1)).deleteById(1);
  }

  @Test
  public void getByOriginAndDestinyNameTest() throws CityNotFoundException {
    OriginCityAndDestinyCityDto cities = OriginCityAndDestinyCityDto.builder()
        .originCity("Mdp")
        .destinyCity("chascomus").build();

    Tariff tariff = Tariff.builder().id(1).pricePerMinute(2f).build();
    Tariff tariff2 = Tariff.builder().id(2).pricePerMinute(3f).build();

    List<Tariff> tariffs = Arrays.asList(tariff, tariff2);

    when(tariffController.findAll(cities)).thenReturn(tariffs);

    ResponseEntity<List<Tariff>> tariffsTest = employeeWebController
        .getByOriginAndDestinyCityName("111", cities);

    assertEquals(tariffsTest.getBody().size(), tariffs.size());
    assertEquals(tariffsTest.getBody().get(0).getId(), tariffs.get(0).getId());
    verify(tariffController, times(2)).findAll(cities);
  }

  @Test
  public void getBillsByUserTest() throws InvalidDateException {
    BetweenDatesDto betweenDatesDto = BetweenDatesDto.builder()
        .start(LocalDate.now())
        .end(LocalDate.now()).build();

    Bill bill = Bill.builder().id(1).date(LocalDate.now()).quantityOfCalls(5).build();
    List<Bill> bills = Collections.singletonList(bill);

    when(billController.findBetweenDates(1, betweenDatesDto)).thenReturn(bills);

    ResponseEntity<List<Bill>> billsReturned = employeeWebController
        .getBillsByUser("111", 1, betweenDatesDto);

    assertEquals(billsReturned.getBody().size(), bills.size());
    assertEquals(billsReturned.getBody().get(0).getDate(), bills.get(0).getDate());
    verify(billController, times(2)).findBetweenDates(1, betweenDatesDto);
  }

  @Test
  public void getCallsByUserTest() throws ClientNotFoundException {
    Call testCall = new Call(1, 2, LocalDate.of(2020, 6, 30), 20f, 25f, null, null, null, null,
        null, null, null);
    Call testCall2 = new Call(1, 2, LocalDate.of(2020, 6, 30), 20f, 25f, null, null, null, null,
        null, null, null);

    List<Call> testsCalls = Arrays.asList(testCall, testCall2);

    when(callController.findCallsFromClient(1)).thenReturn(testsCalls);

    ResponseEntity<List<Call>> listCallsReturned = employeeWebController.getCallsByUser("111", 1);
    assertEquals(listCallsReturned.getBody().size(), testsCalls.size());
    assertEquals(listCallsReturned.getBody().get(0).getDate(), testsCalls.get(0).getDate());
    verify(callController, times(2)).findCallsFromClient(1);
  }

  @Test(expected = CityNotFoundException.class)
  public void getByOriginDestinyNameCityNotFound() throws CityNotFoundException {
    when(tariffController.findAll(OriginCityAndDestinyCityDto.builder().build()))
        .thenThrow(new CityNotFoundException());
    tariffController.findAll(OriginCityAndDestinyCityDto.builder().build());
  }

  @Test(expected = InvalidDateException.class)
  public void getBillsByUserInvalidDate() throws InvalidDateException {
    when(billController.findBetweenDates(1, BetweenDatesDto.builder().build()))
        .thenThrow(new InvalidDateException());
    employeeWebController.getBillsByUser("111", 1, BetweenDatesDto.builder().build());
  }

  @Test(expected = ClientNotFoundException.class)
  public void getCallsByUserNotFound() throws ClientNotFoundException {
    when(callController.findCallsFromClient(1)).thenThrow(new ClientNotFoundException());
    employeeWebController.getCallsByUser("111", 1);
  }

  @Test(expected = PhoneLineAlreadyExists.class)
  public void addPhoneLineAlreadyExists() throws PhoneLineAlreadyExists, ClientNotFoundException {
    when(phoneLineController.save(PhoneLineDto.builder().build(), 1))
        .thenThrow(new PhoneLineAlreadyExists());
    employeeWebController.addPhoneLine("111", PhoneLineDto.builder().build(), 1);
  }

  @Test(expected = PhoneLineNotExists.class)
  public void deletePhoneLineNotExists() throws PhoneLineNotExists {
    doThrow(new PhoneLineNotExists()).when(phoneLineController).deleteById(1);
    employeeWebController.deletePhoneLine("111", 1);
  }

  @Test(expected = PhoneLineNotExists.class)
  public void updatePhoneLineNotExists() throws PhoneLineNotExists {
    PhoneLineDto phoneLineDto = new PhoneLineDto();
    when(phoneLineController.update(phoneLineDto, 0)).thenThrow(new PhoneLineNotExists());
    employeeWebController.updatePhoneLine("111", phoneLineDto, 0);
  }

  @Test(expected = CityNotFoundException.class)
  public void addClientCityNotFound() throws ResourceAlreadyExistException, CityNotFoundException {
    when(clientController.save(UserRequestDto.builder().build()))
        .thenThrow(new CityNotFoundException());
    employeeWebController.addClient("aaa", UserRequestDto.builder().build());
  }

  @Test(expected = ResourceAlreadyExistException.class)
  public void addClientResourceAlreadyExists()
      throws ResourceAlreadyExistException, CityNotFoundException {
    when(clientController.save(UserRequestDto.builder().build()))
        .thenThrow(new ResourceAlreadyExistException());
    employeeWebController.addClient("aaa", UserRequestDto.builder().build());
  }

  @Test(expected = ClientNotFoundException.class)
  public void deleteClientNotFound() throws ClientNotFoundException {
    doThrow(new ClientNotFoundException()).when(clientController).deleteById(1);
    employeeWebController.deleteClient("111", 1);
  }

  @Test(expected = ClientNotFoundException.class)
  public void updateClientNotFound()
      throws ClientNotFoundException, ResourceAlreadyExistException, CityNotFoundException, NoSuchAlgorithmException {
    when(clientController.update(1, UserRequestDto.builder().build()))
        .thenThrow(new ClientNotFoundException());
    employeeWebController.updateClient("aaa", UserRequestDto.builder().build(), 1);
  }

  @Test(expected = ResourceAlreadyExistException.class)
  public void updateClientAlreadyExist()
      throws ClientNotFoundException, ResourceAlreadyExistException, CityNotFoundException, NoSuchAlgorithmException {
    when(clientController.update(1, UserRequestDto.builder().build()))
        .thenThrow(new ResourceAlreadyExistException());
    employeeWebController.updateClient("aaa", UserRequestDto.builder().build(), 1);
  }

  @Test(expected = CityNotFoundException.class)
  public void updateClientCityNotFound()
      throws ClientNotFoundException, ResourceAlreadyExistException, CityNotFoundException, NoSuchAlgorithmException {
    when(clientController.update(1, UserRequestDto.builder().build()))
        .thenThrow(new CityNotFoundException());
    employeeWebController.updateClient("aaa", UserRequestDto.builder().build(), 1);
  }

  @Test(expected = ClientNotFoundException.class)
  public void findClientNotFound() throws ClientNotFoundException {
    when(clientController.findById(1))
        .thenThrow(new ClientNotFoundException());
    employeeWebController.findClient("aaa", 1);
  }

  @Test
  public void testGetBillsFromUserNoContent()
      throws UserNotExistException, InvalidDateException {
    List<Bill> bills = new ArrayList<>();
    BetweenDatesDto betweenDatesDto = BetweenDatesDto.builder().build();
    when(this.billController.findBetweenDates(1, betweenDatesDto)).thenReturn(bills);

    ResponseEntity<?> billsTest = this.employeeWebController
        .getBillsByUser("111", 1, betweenDatesDto);

    Assert.assertEquals(HttpStatus.NO_CONTENT, billsTest.getStatusCode());
  }

  @Test
  public void testGetCallsFromUserNoContent()
      throws UserNotExistException, InvalidDateException, ClientNotFoundException {
    List<Call> calls = new ArrayList<>();
    BetweenDatesDto betweenDatesDto = BetweenDatesDto.builder().build();
    when(this.callController.findCallsFromClient(1)).thenReturn(calls);

    ResponseEntity<?> callsTest = this.employeeWebController
        .getCallsByUser("111", 1);

    Assert.assertEquals(HttpStatus.NO_CONTENT, callsTest.getStatusCode());

  }
}
