package com.utn.phones.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.utn.phones.exceptions.loginExceptions.UserNotExistException;
import com.utn.phones.model.User;
import com.utn.phones.model.UserType;
import com.utn.phones.repositories.UserRepository;
import com.utn.phones.restUtils.HashPassword;
import com.utn.phones.restUtils.RestUtils;
import java.security.NoSuchAlgorithmException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

//import org.junit.Before;
@RunWith(PowerMockRunner.class)
@PrepareForTest(HashPassword.class)
public class UserServiceTest {

  UserService service;

  @Mock
  UserRepository dao;

  @Before
  public void setUp() {
    initMocks(this);
    service = new UserService(dao);
    PowerMockito.mockStatic(HashPassword.class);
  }

  @Test
  public void testLoginOk() throws UserNotExistException, NoSuchAlgorithmException {
    User loggedUser = User.builder().userType(UserType.builder().id(1).build()).active(true)
        .username("user")
        .password("123").id(1).name("user").DNI("123").build();
    when(dao.getByUsernameAndPassword("user", "123")).thenReturn(loggedUser);

    when(HashPassword.hashPassword("123")).thenReturn("123");
    User returnedUser = service.login("user", "123");
    assertEquals(loggedUser.getId(), returnedUser.getId());
    assertEquals(loggedUser.getUsername(), returnedUser.getUsername());
    verify(dao, times(1)).getByUsernameAndPassword("user", "123");
  }

  @Test(expected = UserNotExistException.class)
  public void testLoginUserNotFound() throws UserNotExistException, NoSuchAlgorithmException {
    when(dao.getByUsernameAndPassword("user", "pwd")).thenReturn(null);
    service.login("user", "pwd");
  }


}
