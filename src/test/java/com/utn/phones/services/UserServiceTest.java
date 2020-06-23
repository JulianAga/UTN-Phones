package com.utn.phones.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.utn.phones.exceptions.loginExceptions.UserNotExistException;
import com.utn.phones.model.City;
import com.utn.phones.model.User;
import com.utn.phones.model.UserType;
import com.utn.phones.repositories.UserRepository;
import java.security.NoSuchAlgorithmException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

//import org.junit.Before;

public class UserServiceTest {

  UserService service;

  @Mock
  UserRepository dao;

  @Before
  public void setUp() {
    initMocks(this);
    service = new UserService(dao);
  }

  @Test
  public void testLoginOk() throws UserNotExistException, NoSuchAlgorithmException {
    User loggedUser = new User(1, "123", "foo", "foo", "foo", "foo", new City(),
        new UserType(), true);
    when(dao.getByUsernameAndPassword("user", "pwd")).thenReturn(loggedUser);
    User returnedUser = service.login("user", "pwd");
    assertEquals(loggedUser.getId(), returnedUser.getId());
    assertEquals(loggedUser.getUsername(), returnedUser.getUsername());
    verify(dao, times(1)).getByUsernameAndPassword("user", "pwd");
  }

  @Test(expected = UserNotExistException.class)
  public void testLoginUserNotFound() throws UserNotExistException, NoSuchAlgorithmException {
    when(dao.getByUsernameAndPassword("user", "pwd")).thenReturn(null);
    service.login("user", "pwd");
  }


}
