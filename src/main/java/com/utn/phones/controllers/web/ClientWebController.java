package com.utn.phones.controllers.web;

import com.utn.phones.Sessions.SessionManager;
import com.utn.phones.controllers.BillController;
import com.utn.phones.controllers.CallController;
import com.utn.phones.controllers.ClientController;
import com.utn.phones.dto.BetweenDatesDto;
import com.utn.phones.exceptions.loginExceptions.UserNotexistException;
import com.utn.phones.model.Bill;
import com.utn.phones.model.Call;
import com.utn.phones.projections.MostCalled;
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

  @GetMapping("/top")
  public ResponseEntity<List<MostCalled>> findCallFromClient(
      @RequestHeader("Authorization") String token) throws UserNotexistException {
    return this.callController
        .findMostCalledCities(sessionManager.getCurrentUser(token).getId());
  }

  @GetMapping("/bill")
  public ResponseEntity<List<Bill>> findBillBetweenDates(
      @RequestHeader("Authorization") String token,
      @RequestBody BetweenDatesDto betweenDatesDto)
      throws UserNotexistException {
    return this.billController
        .findBetweenDates(sessionManager.getCurrentUser(token).getId(), betweenDatesDto);
  }

  @GetMapping("/call")
  public ResponseEntity<List<Call>> findCallBetweenDates(
      @RequestHeader("Authorization") String token, @RequestBody BetweenDatesDto betweenDatesDto)
      throws UserNotexistException {
    return this.callController
        .findBetweenDates(betweenDatesDto, sessionManager.getCurrentUser(token).getId());
  }


}
