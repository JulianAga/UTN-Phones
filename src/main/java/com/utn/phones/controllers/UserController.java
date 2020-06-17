package com.utn.phones.controllers;

import com.utn.phones.exceptions.loginExceptions.UserNotExistException;
import com.utn.phones.exceptions.loginExceptions.ValidationException;
import com.utn.phones.model.User;
import com.utn.phones.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public List<User> findAll(){
        return this.userService.findAll();
    }

    public User login(String username, String password) throws UserNotExistException, ValidationException {
        if ((username != null) && (password != null)) {
            return userService.login(username, password);
        } else {
            throw new ValidationException();
        }
    }
}
