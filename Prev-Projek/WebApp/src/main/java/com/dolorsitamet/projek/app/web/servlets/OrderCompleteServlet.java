package com.dolorsitamet.projek.app.web.servlets;

import com.dolorsitamet.projek.app.web.service.CookieManager;
import com.dolorsitamet.projek.app.web.service.Identity;
import com.dolorsitamet.projek.app.web.ws.OrderServiceImpl;
import com.dolorsitamet.projek.app.web.ws.OrderServiceImplService;
import com.dolorsitamet.projek.app.web.ws.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "OrderCompleteServlet", urlPatterns = {"/order_complete"}, loadOnStartup = 1)
public class OrderCompleteServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Identity id = CookieManager.auth(request, response);
    if (id == null) {
      return;
    }

    // Set username.
    request.setAttribute("username", id.name);

    String pickingPoint = request.getParameter("picking_point");
    String destination = request.getParameter("destination");

    // Keep for next page.
    request.setAttribute("pickingPoint", pickingPoint);
    request.setAttribute("destination", destination);

    OrderServiceImplService orderImpl = new OrderServiceImplService();
    OrderServiceImpl order = orderImpl.getOrderServiceImplPort();

    try {
      long driverId = Long.parseLong(request.getParameter("driver_id"));

      User driver = order.getDriverProfile(id.token, driverId);
      request.setAttribute("driver", driver);

      request.getRequestDispatcher("order_complete.jsp").forward(request, response);
    } catch (Exception e) {
      e.printStackTrace();
      throw new ServletException(e);
    }
  }
}
