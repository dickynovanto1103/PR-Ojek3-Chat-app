package com.dolorsitamet.projek.service.web.jws;

import com.dolorsitamet.projek.service.web.entity.Location;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface LocationService {

  @WebMethod
  List<Location> getLocations(String token) throws Exception;

  @WebMethod
  Location getLocation(String token, long locationId) throws Exception;

  @WebMethod
  void saveLocation(String token, Location location) throws Exception;

  @WebMethod
  void deleteLocation(String token, long locationId) throws Exception;
}
