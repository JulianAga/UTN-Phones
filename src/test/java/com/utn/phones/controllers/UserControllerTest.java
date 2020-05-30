package com.utn.phones.controllers;

import static org.mockito.Mockito.mock;

import com.utn.phones.services.UserService;
import org.junit.Before;


public class UserControllerTest {

  UserController userController;
  UserService service;


  @Before
  public void setUp() {
    service = mock(UserService.class);
    userController = new UserController(service);
  }
/*
  @Test
  public void testLoginOk() throws UserNotexistException, ValidationException {
    User loggedUser = new User(1, "Nombre", "username", "", "Surname", null);
    //Cuando llame al mock service.login devuelvo el logged user
    when(service.login("user", "pwd")).thenReturn(loggedUser);
    User returnedUser = userController.login("user", "pwd");

    //Hacemos los assert
    assertEquals(loggedUser.getUserId(), returnedUser.getUserId());
    assertEquals(loggedUser.getUsername(), returnedUser.getUsername());
    verify(service, times(1)).login("user", "pwd");
  }

  @Test(expected = UserNotexistException.class)
  public void testLoginUserNotFound() throws UserNotexistException, ValidationException {
    when(service.login("user", "pwd")).thenThrow(new UserNotexistException());
    userController.login("user", "pwd");
  }

  @Test(expected = ValidationException.class)
  public void testLoginInvalidData() throws UserNotexistException, ValidationException {
    userController.login(null, "pwd");
  }

  @Test
  public void testRemoveUserOk() throws UserNotexistException {
    User userToRemove = new User(1, "Nombre", "username", "", "Surname", null);
    doNothing().when(service).removeUser(userToRemove);
    userController.removeUser(userToRemove);
    verify(service, times(1)).removeUser(userToRemove);
  }

  @Test
  public void testRemoveUsersOk() throws UserNotexistException {
    List<User> usersToRemove = new ArrayList<>();

    usersToRemove.add(new User(1, "Nombre", "username", "", "Surname", null));
    usersToRemove.add(new User(2, "Nombre", "username", "", "Surname", null));
    usersToRemove.add(new User(3, "Nombre", "username", "", "Surname", null));
    doNothing().when(service).removeUser(any());
    userController.removeUsers(usersToRemove);
    verify(service, times(usersToRemove.size())).removeUser(any());
  }


  /*TAREA PARA EL HOGAR : Verificar que falle y que uno de los usuarios no exista , el primero y el segundo existen
   * y el tercero no .
   *
  @Test
  public void testRemoveUsersUserNotExists() throws UserNotexistException {
    List<User> usersToRemove = new ArrayList<>();
    usersToRemove.add(new User(1, "Nombre", "username", "", "Surname", null));
    usersToRemove.add(new User(2, "Nombre", "username", "", "Surname", null));
    usersToRemove.add(new User(3, "Nombre", "username", "", "Surname", null));
    doNothing().when(service).removeUser(any());
    userController.removeUsers(usersToRemove);
    verify(service, times(usersToRemove.size())).removeUser(any());
  }

  */

}
