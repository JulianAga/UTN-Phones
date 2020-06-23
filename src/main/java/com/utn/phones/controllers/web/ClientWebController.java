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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
  @ApiOperation(value = "Consult of the most called destiny by the logged user", code = 200)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Ok"),
          @ApiResponse(code = 401, message = "Unauthorized"),
          @ApiResponse(code = 204, message = "User does not exist"),
          @ApiResponse(code = 204, message = "There are no calls")
  })
  @GetMapping("/top")
  public ResponseEntity<List<MostCalledDto>> findCallFromClient(
      @RequestHeader("Authorization") String token)
      throws UserNotExistException, CallNotFoundException {
    return this.callController
        .findMostCalledCities(sessionManager.getCurrentUser(token).getId()).isEmpty()
        ? ResponseEntity.noContent().build() :
        ResponseEntity.ok(this.callController
            .findMostCalledCities(sessionManager.getCurrentUser(token).getId()));
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
