package com.utn.phones.services;

import com.utn.phones.exceptions.loginExceptions.UserNotExistException;
import com.utn.phones.model.User;
import com.utn.phones.repositories.UserRepository;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    public User login(String username, String password)
        throws UserNotExistException, NoSuchAlgorithmException {
        User user = userRepository.getByUsernameAndPassword(username, hashPassword(password));
        return Optional.ofNullable(user).orElseThrow(UserNotExistException::new);
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        byte[] data = password.getBytes();
        m.update(data, 0, data.length);
        BigInteger i = new BigInteger(1, m.digest());
        System.out.println(String.format("%1$032X", i));
        return String.format("%1$032X", i);
    }

}
