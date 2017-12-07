package com.dolorsitamet.projek.service.web.entity;

import com.dolorsitamet.projek.service.web.common.DataService;

import java.sql.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Transaction model class.
 */
@XmlRootElement(name = "Transaction")
public class Transaction {

  static final String TABLE_NAME = "transactions";

  public Long key;

  @XmlElement(name = "id")
  public Long id;
  @XmlElement(name = "picking_point")
  public String pickingPoint;
  @XmlElement(name = "destination")
  public String destination;
  @XmlElement(name = "driver_id")
  public long driverId;
  @XmlElement(name = "user_id")
  public long userId;
  @XmlElement(name = "rating")
  public int rating;
  @XmlElement(name = "comment")
  public String comment;
  @XmlElement(name = "order_date")
  public Timestamp orderDate;
  @XmlElement(name = "show_user_history")
  public boolean showUserHistory;
  @XmlElement(name = "show_driver_history")
  public boolean showDriverHistory;

  @XmlElement(name = "user")
  private User user;
  @XmlElement(name = "driver")
  private User driver;

  /**
   * Create empty model.
   */
  public Transaction() {
  }

  /**
   * Create empty model forcibly associated with entity.
   *
   * @param key referenced key.
   */
  public Transaction(long key) {
    this.key = key;
  }

  /**
   * Create model from database result set.
   *
   * @param result database result set.
   * @throws SQLException if there are SQL-related errors.
   */
  Transaction(ResultSet result) throws SQLException {
    id = result.getLong("id");
    pickingPoint = result.getString("picking_point");
    destination = result.getString("destination");
    driverId = result.getLong("driver_id");
    userId = result.getLong("user_id");
    rating = result.getInt("rating");
    comment = result.getString("comment");
    orderDate = result.getTimestamp("order_date");
    showUserHistory = result.getInt("show_user_history") == 1;
    showDriverHistory = result.getInt("show_driver_history") == 1;

    key = id;
  }

  /**
   * Get transaction model with specified ID.
   *
   * @param id entity ID.
   * @return transaction model with specified ID.
   * @throws SQLException if there are SQL-related errors.
   */
  public static Transaction getById(long id) throws SQLException {
    Connection connection = DataService.getConnection();
    PreparedStatement statement =
        connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE id=?");
    statement.setLong(1, id);

    ResultSet result = statement.executeQuery();
    return result.next() ? new Transaction(result) : null;
  }

  /**
   * Get driver model associated with this model.
   *
   * @return driver model associated with this model.
   * @throws SQLException if there are SQL-related errors.
   */
  public User getDriver() throws SQLException {
    if (driver == null) {
      driver = User.getById(driverId);
    }

    return driver;
  }

  /**
   * Get user model associated with this model.
   *
   * @return user model associated with this model.
   * @throws SQLException if there are SQL-related errors.
   */
  public User getUser() throws SQLException {
    if (user == null) {
      user = User.getById(userId);
    }

    return user;
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
          "INSERT INTO " + TABLE_NAME + " ("
              + "picking_point, destination, driver_id, user_id, rating, comment, order_date, "
              + "show_user_history, show_driver_history, id) VALUES (?,?,?,?,?,?,?,?,?,?)",
              Statement.RETURN_GENERATED_KEYS);
    } else {
      statement = connection.prepareStatement(
          "UPDATE " + TABLE_NAME + " SET "
              + "picking_point=?, destination=?, driver_id=?, user_id=?, rating=?, comment=?, "
              + "order_date=?, show_user_history=?, show_driver_history=?, id=? WHERE id=?");
      statement.setLong(11, key);
    }

    statement.setString(1, pickingPoint);
    statement.setString(2, destination);
    statement.setLong(3, driverId);
    statement.setLong(4, userId);
    statement.setInt(5, rating);
    statement.setString(6, comment);
    statement.setTimestamp(7, orderDate);
    statement.setInt(8, showUserHistory ? 1 : 0);
    statement.setInt(9, showDriverHistory ? 1 : 0);

    if (id == null) {
      statement.setNull(10, Types.NULL);
    } else {
      statement.setLong(10, id);
    }

    statement.execute();

    // Get auto-increment value if available.

    if (id == null) {
      ResultSet generatedKeys = statement.getGeneratedKeys();

      if (generatedKeys.next()) {
        id = generatedKeys.getLong(1);
      }
    }

    key = id;
  }
}
