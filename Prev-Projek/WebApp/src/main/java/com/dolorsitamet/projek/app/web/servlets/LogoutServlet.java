package com.dolorsitamet.projek.app.web.servlets;

import com.dolorsitamet.projek.app.web.service.CookieManager;
import com.dolorsitamet.projek.app.web.service.Identity;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"}, loadOnStartup = 1)
public class LogoutServlet extends HttpServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Identity id = CookieManager.auth(request, response);
    if (id == null) return;

    Identity.deleteToken(id.token);
    CookieManager.clear(response);

    response.sendRedirect("login.jsp");
  }
}
