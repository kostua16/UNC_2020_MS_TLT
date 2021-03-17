package com.example.demo.controllers;

import javax.security.auth.message.config.AuthConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.example.demo.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Controller;

@Controller
public class LogoutController implements LogoutSuccessHandler {
  @Autowired
  private SecurityConfig config;

  @Override
  public void onLogoutSuccess(
      final HttpServletRequest req,
      final HttpServletResponse res,
      final Authentication authentication
  ) throws IOException {
    if (req.getSession() != null) {
      req.getSession().invalidate();
    }
    String returnTo = "http://localhost:8080/";
    String logoutUrl = config.getLogoutUrl() + "?client_id=" +
        config.getClientId();
    res.sendRedirect(logoutUrl);
  }
}