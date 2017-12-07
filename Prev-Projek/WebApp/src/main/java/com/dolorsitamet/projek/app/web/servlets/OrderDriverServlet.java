package com.dolorsitamet.projek.app.web.servlets;

import com.dolorsitamet.projek.app.web.service.CookieManager;
import com.dolorsitamet.projek.app.web.service.Identity;
import com.dolorsitamet.projek.app.web.ws.*;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "OrderDriverServlet", urlPatterns = {"/order_driver"}, loadOnStartup = 1)
public class OrderDriverServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Identity id = CookieManager.auth(request, response);
    if (id == null) {
      return;
    }

    // Set username.
    request.setAttribute("username", id.name);

    String preferredDriver = request.getParameter("preferred_driver");
    String pickingPoint = request.getParameter("picking_point");
    String destination = request.getParameter("destination");

    // Keep for next page.
    request.setAttribute("pickingPoint", pickingPoint);
    request.setAttribute("destination", destination);

    OrderServiceImplService orderImpl = new OrderServiceImplService();
    OrderServiceImpl order = orderImpl.getOrderServiceImplPort();

    try {
      List<User> driverByName = order.getAvailableDriverByName(id.token, preferredDriver);
      request.setAttribute("preferredDrivers", driverByName);

      List<User> driverByLoc = order.getAvailableDriverByLocation(id.token, pickingPoint);
      request.setAttribute("otherDrivers", driverByLoc);


      request.getRequestDispatcher("order_driver.jsp").forward(request, response);
    } catch (Exception_Exception e) {
      e.printStackTrace();
      throw new ServletException(e);
    }
  }
}
