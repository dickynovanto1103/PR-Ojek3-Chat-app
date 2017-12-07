package com.dolorsitamet.projek.app.web.servlets;

import com.dolorsitamet.projek.app.web.service.CookieManager;
import com.dolorsitamet.projek.app.web.service.Identity;
import com.dolorsitamet.projek.app.web.ws.*;

import java.io.IOException;
import java.lang.Exception;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

@WebServlet(name = "OrderDestinationServlet", urlPatterns = {
    "/order_destination"}, loadOnStartup = 1)
public class OrderDestinationServlet extends HttpServlet {

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

    OrderServiceImplService orderImpl = new OrderServiceImplService();
    OrderServiceImpl order = orderImpl.getOrderServiceImplPort();

    try {
      String comment = request.getParameter("comment");
      Integer rating = Integer.parseInt(request.getParameter("rating"));
      Transaction transaction = new Transaction();
      transaction.setPickingPoint(pickingPoint);
      transaction.setDestination(destination);
      long driverId = Long.parseLong(request.getParameter("driver_id"));
      transaction.setDriverId(driverId);
      transaction.setRating(rating);
      transaction.setComment(comment);
      transaction.setShowUserHistory(true);
      transaction.setShowDriverHistory(true);
      GregorianCalendar gcal = new GregorianCalendar();
      gcal.setTime(new Date());
      XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
      transaction.setOrderDate(xgcal);
      order.applyTransaction(id.token, transaction);
      response.sendRedirect("order_destination");
    } catch (Exception e) {
      e.printStackTrace();
      throw new ServletException(e);
    }
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Identity id = CookieManager.auth(request, response);
    if (id == null) {
      return;
    }

    // Set username.
    request.setAttribute("username", id.name);

    request.getRequestDispatcher("order_destination.jsp").forward(request, response);
  }
}
