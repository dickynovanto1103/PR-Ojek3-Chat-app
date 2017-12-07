package com.dolorsitamet.projek.service.web.jws;

import com.dolorsitamet.projek.service.web.common.Token;
import com.dolorsitamet.projek.service.web.entity.User;
import java.io.IOException;
import java.sql.SQLException;
import javax.jws.WebService;


@WebService
public class ProfileServiceImpl implements ProfileService {

  @Override
  public User getProfile(String token) throws Exception {
    try {
      Token auth = Token.fromToken(token);
      if (auth == null) {
        throw new Exception("Token is not recognized");
      }

      User user = User.getById(auth.id);
      if (user == null) {
        throw new Exception("User is not found");
      }

      if (user.isDriver) {
        user.getLocations();
        user.getPopularity();
      }

      return user;
    } catch (IOException e) {
      e.printStackTrace();
      throw new Exception("Cannot connect to Identity Service");
    } catch (SQLException e) {
      e.printStackTrace();
      throw new Exception("Cannot connect to Database");
    }
  }

  @Override
  public void saveProfile(String token, User user) throws Exception {
    try {
      Token auth = Token.fromToken(token);
      if (auth == null) {
        throw new Exception("Token is not recognized");
      }

      if (user.key == null) {
        // Handle insert operation.
        user.id = auth.id;
      } else {
        // Handle update operation.
        if (user.id != auth.id) {
          throw new Exception("Unauthorized access to unowned object");
        }
      }

      user.save();
    } catch (IOException e) {
      e.printStackTrace();
      throw new Exception("Cannot connect to Identity Service");
    } catch (SQLException e) {
      e.printStackTrace();
      throw new Exception("Cannot connect to Database");
    }
  }
}
