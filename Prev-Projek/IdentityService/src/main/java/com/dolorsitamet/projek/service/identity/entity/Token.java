package com.dolorsitamet.projek.service.identity.entity;

import com.dolorsitamet.projek.service.identity.common.DataService;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

/**
 * Token model class.
 */
public class Token {

  private static final String TABLE_NAME = "tokens";

  private final String key;

  public String token;
  public long accountId;
  public Date expireDate;

  private Account account;

  /**
   * Create empty token model.
   */
  public Token() {
    key = null;
  }

  /**
   * Create empty token model forcibly associated with entity.
   *
   * @param key referenced key.
   */
  public Token(String key) {
    this.key = key;
  }

  /**
   * Create token model from database result set.
   *
   * @param result database result set.
   * @throws SQLException if there are SQL-related errors.
   */
  private Token(ResultSet result) throws SQLException {
    token = result.getString("token");
    accountId = result.getLong("account_id");
    expireDate = result.getDate("expire_date");

    key = token;
  }

  public static Token getByToken(String token) throws SQLException {
    Connection connection = DataService.getConnection();

    PreparedStatement statement =
        connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE token=?");
    statement.setString(1, token);

    ResultSet result = statement.executeQuery();
    return result.next() ? new Token(result) : null;
  }

  /**
   * Get account model from token.
   *
   * @return Account model associated with this token.
   * @throws SQLException if there are SQL-related errors.
   */
  public Account getAccount() throws SQLException {
    if (account == null) {
      account = Account.getById(accountId);
    }

    return account;
  }

  /**
   * Generate and set token using random sequence generator.
   */
  public void generateAndSetToken() {
    SecureRandom random = new SecureRandom();

    byte[] b = new byte[45];
    random.nextBytes(b);

    token = Base64.getUrlEncoder().encodeToString(b);
  }

  /**
   * Synchronize current model with data storage.
   *
   * @throws SQLException if there are SQL-related errors.
   */
  public void save() throws SQLException {
    Connection connection = DataService.getConnection();
    PreparedStatement statement;

    if (key == null) {
      statement = connection.prepareStatement(
          "INSERT INTO " + TABLE_NAME + " (account_id, expire_date, token) VALUES (?, ?, ?)");
    } else {
      statement = connection.prepareStatement(
          "UPDATE " + TABLE_NAME + " SET account_id=?, expire_date=?, token=? WHERE token=?");
      statement.setString(4, key);
    }

    statement.setLong(1, accountId);
    statement.setDate(2, expireDate);
    statement.setString(3, token);

    statement.execute();
  }

  /**
   * Delete referred entity from data storage.
   *
   * @throws SQLException if there are SQL-related errors.
   */
  public void delete() throws SQLException {
    Connection connection = DataService.getConnection();

    PreparedStatement statement =
        connection.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE token=?");
    statement.setString(1, key);

    statement.execute();
  }
}
