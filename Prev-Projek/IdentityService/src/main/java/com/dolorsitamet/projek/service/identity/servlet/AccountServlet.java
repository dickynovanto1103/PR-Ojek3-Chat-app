package com.dolorsitamet.projek.service.identity.servlet;

import com.dolorsitamet.projek.service.identity.entity.Account;
import java.io.IOException;
import java.sql.SQLException;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/account"}, loadOnStartup = 1)
public class AccountServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();

    String name = req.getParameter("username");
    String pass = req.getParameter("password");

    Account account = new Account();
    account.username = name;
    account.setPassword(pass);

    try {
      account.save();

      jsonBuilder
          .add("status", "ok");
    } catch (SQLException e) {
      e.printStackTrace();
      jsonBuilder
          .add("status", "error")
          .add("error_message", e.getMessage());
    }

    resp.setContentType("application/json");

    JsonWriter writer = Json.createWriter(resp.getWriter());
    writer.write(jsonBuilder.build());
    writer.close();
  }
}
