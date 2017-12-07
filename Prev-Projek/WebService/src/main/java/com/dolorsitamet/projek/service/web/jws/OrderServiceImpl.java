package com.dolorsitamet.projek.service.web.jws;

import com.dolorsitamet.projek.service.web.common.Token;
import com.dolorsitamet.projek.service.web.entity.Transaction;
import com.dolorsitamet.projek.service.web.entity.User;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.jws.WebService;

@WebService
public class OrderServiceImpl implements OrderService {

  @Override
  public List<User> getAvailableDriverByName(String token, String preferredDriver)
      throws Exception {
    try {
      Token auth = Token.fromToken(token);
      if (auth == null) {
        throw new Exception("Token is not recognized");
      }

      String driverName = preferredDriver.trim();
      if (driverName.isEmpty()) {
        return null;
      }

      return User.getByName(driverName);
    } catch (IOException e) {
      e.printStackTrace();
      throw new Exception("Unable to contact IdentityService");
    } catch (SQLException e) {
      e.printStackTrace();
      throw new Exception("Unable to contact database");
    }
  }

  @Override
  public List<User> getAvailableDriverByLocation(String token, String pickingPoint) throws Exception {
    try {
      Token auth = Token.fromToken(token);
      if (auth == null) {
        throw new Exception("Token is not recognized");
      }

      return User.getSelectableDrivers(pickingPoint);
    } catch (IOException e) {
      e.printStackTrace();
      throw new Exception("Unable to contact IdentityService");
    } catch (SQLException e) {
      e.printStackTrace();
      throw new Exception("Unable to contact database");
    }
  }

  @Override
  public User getDriverProfile(String token, long driverId) throws Exception {
    try {
      Token auth = Token.fromToken(token);
      if (auth == null) {
        throw new Exception("Token is not recognized");
      }

      User user = User.getById(driverId);
      if (user == null || !user.isDriver) {
        return null;
      }

      return user;
    } catch (IOException e) {
      e.printStackTrace();
      throw new Exception("Unable to contact IdentityService");
    } catch (SQLException e) {
      e.printStackTrace();
      throw new Exception("Unable to contact database");
    }
  }

  @Override
  public void applyTransaction(String token, Transaction transaction) throws Exception {
    try {
      Token auth = Token.fromToken(token);
      if (auth == null) {
        throw new Exception("Token is not recognized");
      }

      // Sanitize input.
      transaction.userId = auth.id;

      transaction.save();
    } catch (IOException e) {
      e.printStackTrace();
      throw new Exception("Unable to contact IdentityService");
    } catch (SQLException e) {
      e.printStackTrace();
      throw new Exception("Unable to contact database");
    }
  }
}
