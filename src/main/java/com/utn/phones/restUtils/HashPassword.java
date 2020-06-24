package com.utn.phones.restUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPassword {

  public static String hashPassword(String password) throws NoSuchAlgorithmException {
    MessageDigest m = MessageDigest.getInstance("MD5");
    byte[] data = password.getBytes();
    m.update(data, 0, data.length);
    BigInteger i = new BigInteger(1, m.digest());
    System.out.println(String.format("%1$032X", i));
    return String.format("%1$032X", i);
  }

}
