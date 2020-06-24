package com.utn.phones.controllers.web.login;

import com.utn.phones.Sessions.SessionManager;
import com.utn.phones.controllers.UserController;
import org.junit.Before;
import org.mockito.Mock;

public class LoginControllerTest {

  @Mock
  SessionManager sessionManager;
  @Mock
  UserController userController;

  LoginController loginController;

  @Before
  public void setUp() {
    loginController = new LoginController(userController, sessionManager);
  }


}
