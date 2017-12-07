package com.dolorsitamet.projek.app.web.servlets;

import com.dolorsitamet.projek.app.web.service.CookieManager;
import com.dolorsitamet.projek.app.web.service.Identity;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"}, loadOnStartup = 1)
public class LoginServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String name = request.getParameter("username");
    String pass = request.getParameter("password");

    String token;

    try {
      token = Identity.createToken(name, pass);
    } catch (ServletException e) {
      request.setAttribute("errorMessage", e.getMessage());
      request.getRequestDispatcher("login.jsp").forward(request, response);
      return;
    }

    CookieManager.apply(token, response);
    response.sendRedirect("profile");
  }
}
