package com.utn.phones.services;

import com.utn.phones.exceptions.loginExceptions.UserNotexistException;
import com.utn.phones.model.User;
import com.utn.phones.repositories.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public User login(String username, String password) throws UserNotexistException {
        User user = userRepository.getByUsernameAndPassword(username, password);
        return Optional.ofNullable(user).orElseThrow(UserNotexistException::new);
    }
}
