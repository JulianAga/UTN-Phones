package com.utn.phones.Sessions;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

@Service
public class SessionFilter extends OncePerRequestFilter {

  @Autowired
  private SessionManager sessionManager;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String sessionToken = request.getHeader("Authorization");
    Session session = sessionManager.getSession(sessionToken);
    if (session != null) {
      filterChain.doFilter(request, response);
    } else {
      response.setStatus(HttpStatus.FORBIDDEN.value());
    }
  }
}
