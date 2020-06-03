package com.utn.phones.services;

import static org.mockito.MockitoAnnotations.initMocks;

import com.utn.phones.repositories.UserRepository;
import org.junit.Before;
import org.mockito.Mock;

public class UserServiceTest {

  UserService service;

  @Mock
  UserRepository dao;

  @Before
  public void setUp() {
    initMocks(this);
    service = new UserService(dao);
  }
/*
  @Test
  public void testLoginOk() throws UserNotexistException, ValidationException {
    User loggedUser = new User(1, "Nombre", "username", "", "Surname", null);
    when(dao.getByUsername("user","pwd")).thenReturn(loggedUser);
    User returnedUser = service.login("user","pwd");
    assertEquals(loggedUser.getUserId(), returnedUser.getUserId());
    assertEquals(loggedUser.getUsername(), returnedUser.getUsername());
    verify(dao, times(1)).getByUsername("user","pwd");
  }

  @Test(expected = UserNotexistException.class)
  public void testLoginUserNotFound() throws UserNotexistException {
    when(dao.getByUsername("user","pwd")).thenReturn(null);
    service.login("user", "pwd");
  }
  */

}
