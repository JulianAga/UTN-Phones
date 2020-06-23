package com.utn.phones.controllers.web;

import com.utn.phones.Sessions.SessionManager;
import com.utn.phones.controllers.BillController;
import com.utn.phones.controllers.CallController;
import com.utn.phones.controllers.ClientController;
import com.utn.phones.dto.BetweenDatesDto;
import com.utn.phones.dto.MostCalledDto;
import com.utn.phones.exceptions.callExceptions.CallNotFoundException;
import com.utn.phones.exceptions.dateExceptions.InvalidDateException;
import com.utn.phones.exceptions.loginExceptions.UserNotExistException;
import com.utn.phones.model.Bill;
import com.utn.phones.model.Call;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ClientWebController {

  private CallController callController;
  private BillController billController;
  private SessionManager sessionManager;

  @Autowired
  public ClientWebController(final ClientController clientController,
      final CallController callController, final BillController billController,
      SessionManager sessionManager) {
    this.callController = callController;
    this.billController = billController;
    this.sessionManager = sessionManager;
  }

  //Consulta de destinos mas llamados por el usuario
  @GetMapping("/top")
  public ResponseEntity<List<MostCalledDto>> findCallFromClient(
      @RequestHeader("Authorization") String token)
      throws CallNotFoundException, UserNotExistException {
    List<MostCalledDto> mostCalledDtoList = callController.findMostCalledCities(sessionManager.getCurrentUser(token).getId());
    return mostCalledDtoList.isEmpty()
        ? ResponseEntity.noContent().build() :
        ResponseEntity.ok(mostCalledDtoList);
  }

  //Consulta del facturas del usuario logueado por rango de fechas
  @GetMapping("/bill")
  public ResponseEntity<List<Bill>> findBillBetweenDates(
      @RequestHeader("Authorization") String token,
      @RequestBody BetweenDatesDto betweenDatesDto)
      throws UserNotExistException, InvalidDateException {
    return this.billController
        .findBetweenDates(sessionManager.getCurrentUser(token).getId(), betweenDatesDto).isEmpty()
        ? ResponseEntity.noContent().build() :
        ResponseEntity.ok(this.billController
            .findBetweenDates(sessionManager.getCurrentUser(token).getId(), betweenDatesDto));
  }

  //Consulta de llamadas del usuario por rango de fechas
  @GetMapping("/call")
  public ResponseEntity<List<Call>> findCallBetweenDates(
      @RequestHeader("Authorization") String token, @RequestBody BetweenDatesDto betweenDatesDto)
      throws UserNotExistException, InvalidDateException {
    return (this.callController
        .findBetweenDates(betweenDatesDto, sessionManager.getCurrentUser(token).getId()).isEmpty())
        ? ResponseEntity.noContent().build() :
        ResponseEntity.ok(this.callController
            .findBetweenDates(betweenDatesDto, sessionManager.getCurrentUser(token).getId()));
  }


}
