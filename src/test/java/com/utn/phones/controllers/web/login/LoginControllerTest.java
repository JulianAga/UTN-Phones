package com.utn.phones.controllers.web.login;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.utn.phones.Sessions.SessionManager;
import com.utn.phones.controllers.UserController;
import com.utn.phones.dto.LoginDto;
import com.utn.phones.exceptions.loginExceptions.InvalidLoginException;
import com.utn.phones.exceptions.loginExceptions.UserNotExistException;
import com.utn.phones.exceptions.loginExceptions.ValidationException;
import com.utn.phones.model.User;
import java.security.NoSuchAlgorithmException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class LoginControllerTest {

  @Mock
  SessionManager sessionManager;
  @Mock
  UserController userController;

  LoginController loginController;

  @Before
  public void setUp() {
    initMocks(this);
    loginController = new LoginController(userController, sessionManager);
  }

  @Test
  public void testLoginOk()
      throws UserNotExistException, NoSuchAlgorithmException, ValidationException, InvalidLoginException {
    LoginDto loginDto = LoginDto.builder()
        .username("test")
        .password("test")
        .build();

    User user = User.builder()
        .username(loginDto.getUsername())
        .password(loginDto.getPassword())
        .build();

    String token = "token";

    when(sessionManager.createSession(user)).thenReturn(token);

    ResponseEntity responseEntity = loginController.login(loginDto);
  }

  @Test(expected = InvalidLoginException.class)
  public void testLoginThenThrowInvalidLogin()
      throws ValidationException, NoSuchAlgorithmException, UserNotExistException, InvalidLoginException {
    LoginDto loginRequestDto = LoginDto.builder()
        .username("test")
        .password("test")
        .build();
    when(userController.login(loginRequestDto.getUsername(), loginRequestDto.getPassword()))
        .thenThrow(new UserNotExistException());
    loginController.login(loginRequestDto);
  }

  @Test(expected = ValidationException.class)
  public void testLoginThenWithInvalidUsernamePassword()
      throws ValidationException, NoSuchAlgorithmException, UserNotExistException, InvalidLoginException {
    LoginDto loginRequestDto = LoginDto.builder()
        .username(null)
        .password(null)
        .build();
    when(userController.login(loginRequestDto.getUsername(), loginRequestDto.getPassword()))
        .thenThrow(new ValidationException());
    loginController.login(loginRequestDto);
  }


  @Test
  public void testLogout() {
    String token = "token";
    doNothing().when(sessionManager).removeSession(token);
    ResponseEntity responseEntity = loginController.logout(token);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    verify(sessionManager, times(1)).removeSession(token);
  }
}