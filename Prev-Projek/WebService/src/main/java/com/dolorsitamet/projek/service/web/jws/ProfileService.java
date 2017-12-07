package com.dolorsitamet.projek.service.web.jws;

import com.dolorsitamet.projek.service.web.entity.User;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface ProfileService {

  @WebMethod
  User getProfile(String token) throws Exception;

  @WebMethod
  void saveProfile(String token, User user) throws Exception;
}
