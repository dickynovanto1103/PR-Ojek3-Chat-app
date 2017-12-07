package com.dolorsitamet.projek.app.web.servlets;

import com.dolorsitamet.projek.app.web.service.CookieManager;
import com.dolorsitamet.projek.app.web.service.Identity;
import com.dolorsitamet.projek.app.web.ws.Exception_Exception;
import com.dolorsitamet.projek.app.web.ws.Location;
import com.dolorsitamet.projek.app.web.ws.LocationServiceImpl;
import com.dolorsitamet.projek.app.web.ws.LocationServiceImplService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "LocationsServlet", urlPatterns = {"/profile_locations"}, loadOnStartup = 1)
public class LocationsServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Identity id = CookieManager.auth(request, response);
    if (id == null) {
      return;
    }

    LocationServiceImplService locationImpl = new LocationServiceImplService();
    LocationServiceImpl location = locationImpl.getLocationServiceImplPort();

    try {
      List<Location> preferredLocations = location.getLocations(id.token);

      request.setAttribute("locations", preferredLocations);
      request.getRequestDispatcher("/locations.jsp").forward(request, response);
    } catch (Exception_Exception e) {
      e.printStackTrace();
      throw new ServletException(e);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Identity id = CookieManager.auth(request, response);
    if (id == null) {
      return;
    }

    LocationServiceImplService locationImpl = new LocationServiceImplService();
    LocationServiceImpl location = locationImpl.getLocationServiceImplPort();

    try {
      String action = request.getParameter("action");

      if ("delete".equals(action)) {
        long locationId = Long.parseLong(request.getParameter("id"));
        location.deleteLocation(id.token, locationId);
      } else if ("update".equals(action)) {
        long locationId = Long.parseLong(request.getParameter("id"));
        Location entity = location.getLocation(id.token, locationId);

        entity.setName(request.getParameter("edit_name"));
        location.saveLocation(id.token, entity);
      } else {
        Location entity = new Location();

        entity.setName(request.getParameter("name"));
        location.saveLocation(id.token, entity);
      }

      response.sendRedirect("profile_locations");
    } catch (Exception_Exception e) {
      e.printStackTrace();
      throw new ServletException(e);
    }
  }
}
