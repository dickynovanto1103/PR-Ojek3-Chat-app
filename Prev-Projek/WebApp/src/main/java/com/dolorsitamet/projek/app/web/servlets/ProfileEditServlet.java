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

@WebServlet(name = "ProfileEditServlet", urlPatterns = {"/profile_edit"}, loadOnStartup = 1)
public class ProfileEditServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Identity id = CookieManager.auth(request, response);
    if (id == null) {
      return;
    }

    // Set username.
    request.setAttribute("username", id.name);

    ProfileServiceImplService profileImpl = new ProfileServiceImplService();
    ProfileServiceImpl profile = profileImpl.getProfileServiceImplPort();

    try {
      User user = profile.getProfile(id.token);

      request.setAttribute("user", user);
      request.getRequestDispatcher("/profile_edit.jsp").forward(request, response);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Identity id = CookieManager.auth(request, response);
    if (id == null) {
      return;
    }

    ProfileServiceImplService profileImpl = new ProfileServiceImplService();
    ProfileServiceImpl profile = profileImpl.getProfileServiceImplPort();

    try {
      String fullName = request.getParameter("full_name");
      String phoneNumber = request.getParameter("phone_number");
      String isDriver = request.getParameter("is_driver");

      User user = profile.getProfile(id.token);

      user.setFullName(fullName);
      user.setPhoneNumber(phoneNumber);
      user.setDriver(isDriver != null);

      profile.saveProfile(id.token, user);

      response.sendRedirect("profile");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}