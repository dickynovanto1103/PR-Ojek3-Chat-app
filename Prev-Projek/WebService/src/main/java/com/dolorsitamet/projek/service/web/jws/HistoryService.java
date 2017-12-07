package com.dolorsitamet.projek.service.web.jws;

import com.dolorsitamet.projek.service.web.entity.Transaction;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface HistoryService {

  @WebMethod
  List<Transaction> getVisibleUserHistory(String token) throws Exception;

  @WebMethod
  List<Transaction> getVisibleDriverHistory(String token) throws Exception;

  @WebMethod
  void hideUserHistoryItem(String token, long transactionId) throws Exception;

  @WebMethod
  void hideDriverHistoryItem(String token, long transactionId) throws Exception;
}
