package com.utn.phones.Sessions;

import com.utn.phones.exceptions.loginExceptions.UserNotexistException;
import com.utn.phones.model.User;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;


@Component
public class SessionManager {

  Map<String, Session> sessionMap = new Hashtable<>();

  long sessionExpiration = 10000000;

  public String createSession(User user) {
    String token = UUID.randomUUID().toString();
    sessionMap.put(token, new Session(token, user, new Date(System.currentTimeMillis())));
    return token;
  }

  public Session getSession(String token) {
    if (token == null) {
      return null;
    } else {
      Session session = sessionMap.get(token);

      if (session != null) {
        session.setLastAction(new Date(System.currentTimeMillis()));
      }
      return session;
    }
  }

  public void removeSession(String token) {
    sessionMap.remove(token);
  }

  public void expireSessions() {
    for (String k : sessionMap.keySet()) {
      Session v = sessionMap.get(k);
      if (v.getLastAction().getTime() < System.currentTimeMillis() + (sessionExpiration * 1000)) {
        System.out.println("Expiring session " + k);
        sessionMap.remove(k);
      }
    }
  }

  public User getCurrentUser(String token) throws UserNotexistException {
    return Optional.ofNullable(getSession(token))
        .map(Session::getLoggedUser)
        .orElseThrow(UserNotexistException::new);
  }
}
