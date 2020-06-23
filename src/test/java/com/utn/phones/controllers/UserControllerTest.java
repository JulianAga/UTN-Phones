package com.utn.phones.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.utn.phones.exceptions.loginExceptions.UserNotExistException;
import com.utn.phones.exceptions.loginExceptions.ValidationException;
import com.utn.phones.model.City;
import com.utn.phones.model.Employee;
import com.utn.phones.model.User;
import com.utn.phones.model.UserType;
import com.utn.phones.services.UserService;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;


public class UserControllerTest {

  UserController userController;
  UserService service;


  @Before
  public void setUp() {
    service = mock(UserService.class);
    userController = new UserController(service);
  }

  @Test
  public void testFindAll(){
    User userTest = new User(1, "321", "user", "pwd", "", "", new City(),
        new UserType(), true);
    User userTest2 = new User(2, "123", "asd", "asd", "", "", new City(),
        new UserType(), true);
    List<User> userList = Arrays.asList(userTest,userTest2);

    when(service.findAll()).thenReturn(userList);

    List<User> userListTest = userController.findAll();

    assertEquals(userList.size(),userListTest.size());
    assertEquals(userList.get(1).getId(),userListTest.get(1).getId());
  }

  @Test
  public void testLoginOk()
      throws UserNotExistException, ValidationException, NoSuchAlgorithmException {
    User loggedUser = new User(1, "", "user", "pwd", "sad", "sad", new City(), new UserType(),
        true);
    //Cuando llame al mock service.login devuelvo el logged user
    when(service.login("user", "pwd")).thenReturn(loggedUser);
    User returnedUser = userController.login("user", "pwd");

    //Hacemos los assert
    assertEquals(loggedUser.getId(), returnedUser.getId());
    assertEquals(loggedUser.getUsername(), returnedUser.getUsername());
    verify(service, times(1)).login("user", "pwd");
  }

  @Test(expected = UserNotExistException.class)
  public void testLoginUserNotFound()
      throws UserNotExistException, ValidationException, NoSuchAlgorithmException {
    when(service.login("user", "pwd")).thenThrow(new UserNotExistException());
    userController.login("user", "pwd");
  }

  @Test(expected = ValidationException.class)
  public void testLoginInvalidData()
      throws UserNotExistException, ValidationException, NoSuchAlgorithmException {
    userController.login(null, "pwd");
  }
/*
  @Test
  public void testRemoveUserOk() throws UserNotExistException {
    User userToRemove = new User(1, "Nombre", "username", "", "Surname", null);
    doNothing().when(service).removeUser(userToRemove);
    userController.removeUser(userToRemove);
    verify(service, times(1)).removeUser(userToRemove);
  }

  @Test
  public void testRemoveUsersOk() throws UserNotexistException {
    List<User> usersToRemove = new ArrayList<>();

    usersToRemove.add(new User(1, "carlos", "asd", "asd", "sad", "sad", new City(), new UserType(),
        true));
    usersToRemove.add(new User(2, "Nombre", "username", "", "Surname", null));
    usersToRemove.add(new User(3, "Nombre", "username", "", "Surname", null));
    doNothing().when(service).removeUser(any());
    userController.removeUsers(usersToRemove);
    verify(service, times(usersToRemove.size())).removeUser(any());
  }

*/
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
