package com.dolorsitamet.projek.service.web.jws;

import com.dolorsitamet.projek.service.web.common.Token;
import com.dolorsitamet.projek.service.web.entity.Location;
import com.dolorsitamet.projek.service.web.entity.User;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.jws.WebService;

@WebService
public class LocationServiceImpl implements LocationService {

  @Override
  public List<Location> getLocations(String token) throws Exception {
    try {
      Token auth = Token.fromToken(token);
      if (auth == null) {
        throw new Exception("Token is not recognized");
      }

      User user = User.getById(auth.id);
      if (user == null) {
        throw new Exception("User is not found");
      }

      return user.getLocations();
    } catch (IOException e) {
      e.printStackTrace();
      throw new Exception("Unable to contact IdentityService");
    } catch (SQLException e) {
      e.printStackTrace();
      throw new Exception("Unable to contact database");
    }
  }

  @Override
  public Location getLocation(String token, long locationId) throws Exception {
    try {
      Token auth = Token.fromToken(token);
      if (auth == null) {
        throw new Exception("Token is not recognized");
      }

      User user = User.getById(auth.id);
      if (user == null) {
        throw new Exception("User is not found");
      }

      Location location = Location.getById(locationId);
      if (location == null) {
        throw new Exception("Location is not found");
      }

      if (location.driverId != user.id) {
        throw new Exception("Unauthorized access to unowned object");
      }

      return location;
    } catch (IOException e) {
      e.printStackTrace();
      throw new Exception("Unable to contact IdentityService");
    } catch (SQLException e) {
      e.printStackTrace();
      throw new Exception("Unable to contact database");
    }
  }

  @Override
  public void saveLocation(String token, Location location) throws Exception {
    try {
      Token auth = Token.fromToken(token);
      if (auth == null) {
        throw new Exception("Token is not recognized");
      }

      if (location.key == null) {
        // Handle insert operation.
        location.driverId = auth.id;
      } else {
        // Handle update operation.
        Location storedLocation = Location.getById(location.id);

        if (storedLocation != null && storedLocation.driverId != auth.id) {
          throw new Exception("Unauthorized access to unowned object");
        }
      }

      location.save();
    } catch (IOException e) {
      e.printStackTrace();
      throw new Exception("Unable to contact IdentityService");
    } catch (SQLException e) {
      e.printStackTrace();
      throw new Exception("Unable to contact database");
    }
  }

  @Override
  public void deleteLocation(String token, long locationId) throws Exception {
    try {
      Token auth = Token.fromToken(token);
      if (auth == null) {
        throw new Exception("Token is not recognized");
      }

      Location location = Location.getById(locationId);
      if (location == null) {
        throw new Exception("Location is not found");
      }

      if (location.driverId != auth.id) {
        throw new Exception("Unauthorized access to unowned object");
      }

      location.delete();
    } catch (IOException e) {
      e.printStackTrace();
      throw new Exception("Unable to contact IdentityService");
    } catch (SQLException e) {
      e.printStackTrace();
      throw new Exception("Unable to contact database");
    }
  }
}
