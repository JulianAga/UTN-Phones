package com.utn.phones.controllers.web.login;

import com.utn.phones.Sessions.SessionManager;
import com.utn.phones.controllers.UserController;
import com.utn.phones.dto.LoginDto;
import com.utn.phones.exceptions.loginExceptions.InvalidLoginException;
import com.utn.phones.exceptions.loginExceptions.UserNotexistException;
import com.utn.phones.exceptions.loginExceptions.ValidationException;
import com.utn.phones.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("")
public class LoginController {

  private final UserController userController;
  private final SessionManager sessionManager;

  @Autowired
  public LoginController(UserController userController, SessionManager sessionManager) {
    this.userController = userController;
    this.sessionManager = sessionManager;
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginDto loginDto)
      throws InvalidLoginException, UserNotexistException, ValidationException {
    try {
      User user = userController.login(loginDto.getUsername(), loginDto.getPassword());
      String token = sessionManager.createSession(user);
      return ResponseEntity.ok(token);
    } catch (UserNotexistException e) {
      throw new InvalidLoginException();
    }
  }

  @PostMapping("/logout")
  public ResponseEntity logout(@RequestHeader("Authorization") String token) {
    sessionManager.removeSession(token);
    return ResponseEntity.ok().build();
  }

  public HttpHeaders createHeaders(String token) {
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.set("Authorization", token);
    return responseHeaders;
  }
}
