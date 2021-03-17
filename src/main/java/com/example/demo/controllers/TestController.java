package com.example.demo.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @GetMapping(value = "main")
  public String getMainPage(final HttpServletRequest request) {
    return "Hello!";
  }

  @GetMapping(value = "communal")
  public String getCommunalPage(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final Authentication authentication
  ) {
    return "Hello from communal page!";
  }
}
