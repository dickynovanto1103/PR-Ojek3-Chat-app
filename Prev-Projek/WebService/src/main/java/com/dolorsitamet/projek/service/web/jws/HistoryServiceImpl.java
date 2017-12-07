package com.dolorsitamet.projek.service.web.jws;

import com.dolorsitamet.projek.service.web.common.Token;
import com.dolorsitamet.projek.service.web.entity.Transaction;
import com.dolorsitamet.projek.service.web.entity.User;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.jws.WebService;

@WebService(endpointInterface = "com.dolorsitamet.projek.service.web.jws.HistoryService")
public class HistoryServiceImpl implements HistoryService {

  @Override
  public List<Transaction> getVisibleUserHistory(String token) throws Exception {
    try {
      Token auth = Token.fromToken(token);
      if (auth == null) {
        throw new Exception("Token is not recognized");
      }

      User user = User.getById(auth.id);
      if (user == null) {
        throw new Exception("User is not found");
      }

      return user.getVisibleUserTransactions();
    } catch (IOException e) {
      e.printStackTrace();
      throw new Exception("Unable to contact IdentityService");
    } catch (SQLException e) {
      e.printStackTrace();
      throw new Exception("Unable to contact database");
    }
  }

  @Override
  public List<Transaction> getVisibleDriverHistory(String token) throws Exception {
    try {
      Token auth = Token.fromToken(token);
      if (auth == null) {
        throw new Exception("Token is not recognized");
      }

      User user = User.getById(auth.id);
      if (user == null) {
        throw new Exception("User is not found");
      }

      return user.getVisibleDriverTransactions();
    } catch (IOException e) {
      e.printStackTrace();
      throw new Exception("Unable to contact IdentityService");
    } catch (SQLException e) {
      e.printStackTrace();
      throw new Exception("Unable to contact database");
    }
  }

  @Override
  public void hideUserHistoryItem(String token, long transactionId) throws Exception {
    try {
      Token auth = Token.fromToken(token);
      if (auth == null) {
        throw new Exception("Token is not recognized");
      }

      User user = User.getById(auth.id);
      if (user == null) {
        throw new Exception("User is not found");
      }

      Transaction transaction = Transaction.getById(transactionId);
      if (transaction == null) {
        throw new Exception("Transaction is not found");
      }

      if (user.id != transaction.userId) {
        throw new Exception("Unauthorized access to unowned object");
      }

      transaction.showUserHistory = false;
      transaction.save();
    } catch (IOException e) {
      e.printStackTrace();
      throw new Exception("Unable to contact IdentityService");
    } catch (SQLException e) {
      e.printStackTrace();
      throw new Exception("Unable to contact database");
    }
  }

  @Override
  public void hideDriverHistoryItem(String token, long transactionId) throws Exception {
    try {
      Token auth = Token.fromToken(token);
      if (auth == null) {
        throw new Exception("Token is not recognized");
      }

      User user = User.getById(auth.id);
      if (user == null) {
        throw new Exception("User is not found");
      }

      Transaction transaction = Transaction.getById(transactionId);
      if (transaction == null) {
        throw new Exception("Transaction is not found");
      }

      if (user.id != transaction.driverId) {
        throw new Exception("Unauthorized access to unowned object");
      }

      transaction.showDriverHistory = false;
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
