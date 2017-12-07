package com.dolorsitamet.projek.service.identity.entity;

import com.dolorsitamet.projek.service.identity.common.DataService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Account model class.
 */
public class Account {

  private static final String TABLE_NAME = "accounts";

  private final Long key;

  public long id;
  public String username;
  private String password;

  /**
   * Create empty account model.
   */
  public Account() {
    key = null;
  }

  /**
   * Create empty account model forcibly associated with entity.
   *
   * @param key reference key.
   */
  public Account(long key) {
    this.key = key;
  }

  /**
   * Create account model from database result set.
   *
   * @param result database result set.
   * @throws SQLException if there are SQL-related errors.
   */
  private Account(ResultSet result) throws SQLException {
    id = result.getLong("id");
    username = result.getString("username");
    password = result.getString("password");

    key = id;
  }

  /**
   * Get account by account ID.
   *
   * @param id account ID.
   * @return Account if account with specified ID exists.
   * @throws SQLException if there are SQL-related errors.
   */
  public static Account getById(long id) throws SQLException {
    Connection connection = DataService.getConnection();

    PreparedStatement statement =
        connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE id=?");
    statement.setLong(1, id);

    ResultSet result = statement.executeQuery();
    return result.next() ? new Account(result) : null;
  }

  /**
   * Get account by account name.
   *
   * @param name account name.
   * @return Account if account with specified name exists.
   * @throws SQLException if there are SQL-related errors.
   */
  public static Account getByName(String name) throws SQLException {
    Connection connection = DataService.getConnection();

    PreparedStatement statement =
        connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE username=?");
    statement.setString(1, name);

    ResultSet result = statement.executeQuery();
    return result.next() ? new Account(result) : null;
  }

  /**
   * Check if specified password matches.
   *
   * @param password password to check.
   * @return true if matches, otherwise false.
   */
  public boolean checkPassword(String password) {
    return BCrypt.checkpw(password, this.password);
  }

  /**
   * Set and hash password.
   *
   * @param password password to be set and hashed.
   */
  public void setPassword(String password) {
    this.password = BCrypt.hashpw(password, BCrypt.gensalt());
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
          "INSERT INTO " + TABLE_NAME + " (username, password) VALUES (?, ?)");
    } else {
      statement = connection.prepareStatement(
          "UPDATE " + TABLE_NAME + " SET username=?, password=?, id=? WHERE id=?");
      statement.setLong(3, id);
      statement.setLong(4, key);
    }

    statement.setString(1, username);
    statement.setString(2, password);

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
        connection.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE id=?");
    statement.setLong(1, key);

    statement.execute();
  }
}
