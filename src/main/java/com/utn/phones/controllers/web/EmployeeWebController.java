package com.utn.phones.controllers.web;

import com.utn.phones.controllers.BillController;
import com.utn.phones.controllers.CallController;
import com.utn.phones.controllers.ClientController;
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
import com.utn.phones.exceptions.phoneLinesExceptions.PhoneLineAlreadyExists;
import com.utn.phones.exceptions.phoneLinesExceptions.PhoneLineNotExists;
import com.utn.phones.model.Bill;
import com.utn.phones.model.Call;
import com.utn.phones.model.City;
import com.utn.phones.model.Client;
import com.utn.phones.model.PhoneLine;
import com.utn.phones.model.Tariff;
import com.utn.phones.restUtils.RestUtils;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/back-office")
public class EmployeeWebController {

  private TariffController tariffController;

  private ClientController clientController;

  private BillController billController;

  private PhoneLineController phoneLineController;

  private CallController callController;

  @Autowired
  public EmployeeWebController(TariffController tariffController,
      ClientController clientController, BillController billController,
      PhoneLineController phoneLineController, CallController callController) {
    this.tariffController = tariffController;
    this.clientController = clientController;
    this.billController = billController;
    this.phoneLineController = phoneLineController;
    this.callController = callController;
  }

  //Consulta de tarifas
  @GetMapping("/tariff")
  public ResponseEntity<List<Tariff>> getByOriginAndDestinyCityName(
      @RequestHeader("Authorization") String token, @RequestBody
      OriginCityAndDestinyCityDto citiesDto) throws CityNotFoundException {
    return (this.tariffController.findAll(citiesDto).isEmpty()) ? ResponseEntity.noContent().build()
        :
            ResponseEntity.ok(this.tariffController.findAll(citiesDto));
  }

  //Consulta de facturación
  @GetMapping("/bill/{id}")
  public ResponseEntity<List<Bill>> getBillsByUser(@RequestHeader("Authorization") String token,
      @PathVariable Integer id, @RequestBody
      BetweenDatesDto betweenDatesDto) throws InvalidDateException {
    return (this.billController.findBetweenDates(id, betweenDatesDto)).isEmpty() ? ResponseEntity
        .noContent().build()
        : ResponseEntity.ok(this.billController.findBetweenDates(id, betweenDatesDto));
  }

  //Consulta de llamadas por usuario
  @GetMapping("/call/{id}")
  public ResponseEntity<List<Call>> getCallsByUser(@RequestHeader("Authorization") String token,
      @PathVariable Integer id)
      throws ClientNotFoundException {
    return (this.callController.findCallsFromClient(id).isEmpty()) ? ResponseEntity.noContent()
        .build()
        : ResponseEntity.ok(this.callController.findCallsFromClient(id));
  }

  /* ---------------Alta, baja y suspensión de lineas--------------- */
  //Alta de linea
  @PostMapping("/phone-line")
  public ResponseEntity<?> addPhoneLine(@RequestHeader("Authorization") String token,
      @RequestBody PhoneLine phoneLine, @RequestBody City city)
      throws PhoneLineAlreadyExists {
    return ResponseEntity
        .created(RestUtils.getPhoneLineLocation(this.phoneLineController.save(phoneLine, city)))
        .build();
  }

  //Baja de linea
  @DeleteMapping("/phone-line/{id}")
  public ResponseEntity<?> deletePhoneLine(@RequestHeader("Authorization") String token,
      @PathVariable Integer id) throws PhoneLineNotExists {
    this.phoneLineController.deleteById(id);
    return ResponseEntity.ok().build();
  }

  //Suspensión de linea
  @PutMapping("/phone-line/{id}")
  public ResponseEntity<PhoneLine> updatePhoneLine(@RequestHeader("Authorization") String token,
      @RequestBody PhoneLineDto phoneLineDto,
      @RequestBody City city, @PathVariable Integer id)
      throws PhoneLineNotExists {
    return ResponseEntity.ok(this.phoneLineController.update(phoneLineDto, city, id));
  }


  /* ---------------Manejo de clientes--------------- */

  //Alta de cliente
  @PostMapping("/client")
  public ResponseEntity<?> addClient(@RequestHeader("Authorization") String token,
      @RequestBody UserRequestDto client)
      throws CityNotFoundException, ResourceAlreadyExistException {
    URI uri = RestUtils.getClientLocation(this.clientController.save(client));
    return ResponseEntity.created(uri).body(uri.toString());
  }

  //Baja de cliente
  @DeleteMapping("/client/{id}")
  public ResponseEntity<?> deleteClient(@RequestHeader("Authorization") String token,
      @PathVariable Integer id) throws ClientNotFoundException {
    this.clientController.deleteById(id);
    return ResponseEntity.ok().build();
  }

  //Update del cliente
  @PutMapping("/client/{id}")
  public ResponseEntity<Client> updateClient(@RequestHeader("Authorization") String token,
      @RequestBody UserRequestDto client,
      @PathVariable Integer id)
      throws ClientNotFoundException, CityNotFoundException, ResourceAlreadyExistException {
    return ResponseEntity.ok(this.clientController.update(id, client));
  }

  //Obtener cliente
  @GetMapping("/client/{id}")
  public ResponseEntity<Client> findClient(@RequestHeader("Authorization") String token,
      @PathVariable Integer id) throws ClientNotFoundException {
    return ResponseEntity.ok(this.clientController.findById(id));
  }
}
