package com.dolorsitamet.projek.app.web.servlets;

import com.dolorsitamet.projek.app.web.service.CookieManager;
import com.dolorsitamet.projek.app.web.service.Identity;
import com.dolorsitamet.projek.app.web.ws.ProfileServiceImpl;
import com.dolorsitamet.projek.app.web.ws.ProfileServiceImplService;
import com.dolorsitamet.projek.app.web.ws.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"}, loadOnStartup = 1)
public class RegisterServlet extends HttpServlet {

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String fullName = request.getParameter("full_name");
    String username = request.getParameter("username");
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    String phoneNumber = request.getParameter("phone_number");
    String isDriver = request.getParameter("is_driver");

    // Create account on IdentityService.

    String token = Identity.createAccountAndToken(username, password);
    CookieManager.apply(token, response);

    // Create user on WebService.

    ProfileServiceImplService profileImpl = new ProfileServiceImplService();
    ProfileServiceImpl profile = profileImpl.getProfileServiceImplPort();

    User user = new User();

    user.setFullName(fullName);
    user.setEmail(email);
    user.setPhoneNumber(phoneNumber);

    boolean inputIsDriver = (isDriver != null && isDriver.equals("on"));
    user.setDriver(inputIsDriver);

    try {
      profile.saveProfile(token, user);

      response.sendRedirect("profile");
    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}
