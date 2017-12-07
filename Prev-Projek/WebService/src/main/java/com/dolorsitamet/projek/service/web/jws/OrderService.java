package com.dolorsitamet.projek.service.web.jws;

import com.dolorsitamet.projek.service.web.entity.Transaction;
import com.dolorsitamet.projek.service.web.entity.User;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface OrderService {

  @WebMethod
  List<User> getAvailableDriverByName(String token, String preferredDriver) throws Exception;

  @WebMethod
  List<User> getAvailableDriverByLocation(String token, String pickingPoint) throws Exception;

  @WebMethod
  User getDriverProfile(String token, long driverId) throws Exception;

  @WebMethod
  void applyTransaction(String token, Transaction transaction) throws Exception;
}
