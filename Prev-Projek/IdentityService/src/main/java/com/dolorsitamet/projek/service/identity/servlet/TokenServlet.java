package com.dolorsitamet.projek.service.identity.servlet;

import com.dolorsitamet.projek.service.identity.entity.Account;
import com.dolorsitamet.projek.service.identity.entity.Token;
import java.io.IOException;
import java.sql.Date;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/token"}, loadOnStartup = 1)
public class TokenServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();

    try {
      String reqToken = req.getParameter("token");

      Token token = Token.getByToken(reqToken);

      if (token == null) {
        throw new Exception("Token is invalid");
      }

      jsonBuilder
          .add("status", "ok")
          .add("user_id", token.accountId)
          .add("user_name", token.getAccount().username);
    } catch (Exception e) {
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

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();

    try {
      String name = req.getParameter("username");
      String pass = req.getParameter("password");

      Account account = Account.getByName(name);

      if (account == null) {
        throw new ServletException("Invalid username or password");
      }

      if (!account.checkPassword(pass)) {
        throw new ServletException("Invalid username or password");
      }

      Token token = new Token();

      token.accountId = account.id;
      token.expireDate = new Date(new java.util.Date().getTime() + (7 * 24 * 3600 * 1000));
      token.generateAndSetToken();

      token.save();

      jsonBuilder
          .add("status", "ok")
          .add("token", token.token);
    } catch (Exception e) {
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

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();

    try {
      String reqToken = req.getParameter("token");

      Token token = new Token(reqToken);
      token.delete();

      jsonBuilder
          .add("status", "ok");
    } catch (Exception e) {
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
