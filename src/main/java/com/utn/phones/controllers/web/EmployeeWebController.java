package com.utn.phones.controllers.web;

import com.utn.phones.controllers.BillController;
import com.utn.phones.controllers.CallController;
import com.utn.phones.controllers.ClientController;
import com.utn.phones.controllers.PhoneLineController;
import com.utn.phones.controllers.TariffController;
import com.utn.phones.dto.OriginCityAndDestinyCityDto;
import com.utn.phones.model.Call;
import com.utn.phones.model.PhoneLine;
import com.utn.phones.model.Tariff;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @GetMapping("/tariff")
  public ResponseEntity<List<Tariff>> getByOriginAndDestinyCityName(
      OriginCityAndDestinyCityDto citiesDto) {
    return this.tariffController.findByOriginNameAndDestinyName(citiesDto);
  }

  @PostMapping("/phone-line")
  public void addPhoneLine(@RequestBody PhoneLine phoneLine) {
    this.phoneLineController.save(phoneLine);
  }

  @GetMapping("/call/{id}")
  public ResponseEntity<List<Call>> getCallsByUser(@PathVariable Integer id) {
    return this.callController.findCallsFromClient(id);
  }


}
