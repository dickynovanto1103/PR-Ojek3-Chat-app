package com.dolorsitamet.projek.app.web.service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieManager {

  private static final String AUTH_COOKIE = "auth";

  private static String extractToken(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();

    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (AUTH_COOKIE.equals(cookie.getName())) {
          return cookie.getValue();
        }
      }
    }

    return null;
  }

  public static void clear(HttpServletResponse response) {
    Cookie authCookie = new Cookie(AUTH_COOKIE, "");
    authCookie.setMaxAge(0);

    response.addCookie(authCookie);
  }

  public static void apply(String token, HttpServletResponse response) {
    Cookie authCookie = new Cookie(AUTH_COOKIE, token);

    response.addCookie(authCookie);
  }

  public static Identity auth(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    String token = extractToken(request);

    // Check for unset cookie.
    if (token == null) {
      response.sendRedirect("login.jsp");
      return null;
    }

    Identity id = Identity.fromToken(token);

    // Check for invalid token.
    if (id == null) {
      response.sendRedirect("login.jsp");
      return null;
    }

    return id;
  }
}
