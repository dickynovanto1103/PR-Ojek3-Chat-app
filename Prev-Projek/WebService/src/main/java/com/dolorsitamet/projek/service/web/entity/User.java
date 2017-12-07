package com.dolorsitamet.projek.service.web.entity;

import com.dolorsitamet.projek.service.web.common.DataService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * User model class.
 */
@XmlRootElement(name = "User")
public class User {

  private static final String TABLE_NAME = "users";

  public Long key = null;

  @XmlElement(name = "id")
  public Long id;
  @XmlElement(name = "full_name")
  public String fullName;
  @XmlElement(name = "email")
  public String email;
  @XmlElement(name = "phone_number")
  public String phoneNumber;
  @XmlElement(name = "driver")
  public boolean isDriver;

  @XmlElement(name = "popularity")
  private Popularity popularity;
  @XmlElement(name = "locations")
  private List<Location> locations;
  @XmlElement(name = "visible_user_transactions")
  private List<Transaction> visibleUserTransactions;
  @XmlElement(name = "visible_driver_transactions")
  private List<Transaction> visibleDriverTransactions;

  /**
   * Create empty model.
   */
  public User() {
  }

  /**
   * Create empty model forcibly associated with entity.
   *
   * @param key referenced key.
   */
  public User(long key) {
    this.key = key;
  }

  /**
   * Create model from database result set.
   *
   * @param result database result set.
   * @throws SQLException if there are SQL-related errors.
   */
  private User(ResultSet result) throws SQLException {
    id = result.getLong("id");
    fullName = result.getString("full_name");
    email = result.getString("email");
    phoneNumber = result.getString("phone_number");
    isDriver = result.getInt("is_driver") == 1;

    key = id;
  }

  /**
   * Get user model with specified ID.
   *
   * @param id entity ID.
   * @return user model with specified ID.
   * @throws SQLException if there are SQL-related errors.
   */
  public static User getById(long id) throws SQLException {
    Connection connection = DataService.getConnection();
    PreparedStatement statement =
        connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE id=?");

    statement.setLong(1, id);

    ResultSet result = statement.executeQuery();
    return result.next() ? new User(result) : null;
  }


  public static List<User> getByName(String name) throws SQLException{
    Connection connection = DataService.getConnection();
    PreparedStatement statement =
            connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE full_name LIKE ?");

    statement.setString(1, "%" + name + "%");

    ResultSet result = statement.executeQuery();
    ArrayList<User> data = new ArrayList<>();

    while (result.next()) {
      data.add(new User(result));
    }

    return data;
  }

  /**
   * Get user models based on preferred locations.
   *
   * @param pickupLocation preferred location.
   * @return user models based on preferred locations.
   * @throws SQLException if there are SQL-related errors.
   */
  public static List<User> getSelectableDrivers(String pickupLocation) throws SQLException {
    Connection connection = DataService.getConnection();
    PreparedStatement statement = connection.prepareStatement(
        "SELECT * FROM " + TABLE_NAME
            + " WHERE is_driver AND id IN (SELECT driver_id FROM locations WHERE name = ?)");

    statement.setString(1, pickupLocation);

    ResultSet result = statement.executeQuery();
    ArrayList<User> data = new ArrayList<>();

    while (result.next()) {
      data.add(new User(result));
    }

    return data;
  }

  /**
   * Get location models associated with this user.
   *
   * @return location models associated with this user.
   * @throws SQLException if there are SQL-related errors.
   */
  public List<Location> getLocations() throws SQLException {
    if (locations == null) {
      Connection connection = DataService.getConnection();
      PreparedStatement statement = connection.prepareStatement(
          "SELECT * FROM " + Location.TABLE_NAME + " WHERE driver_id=?");

      statement.setLong(1, key);

      ResultSet result = statement.executeQuery();
      List<Location> data = new ArrayList<>();

      while (result.next()) {
        data.add(new Location(result));
      }

      locations = data;
    }

    return locations;
  }

  /**
   * Get visible user transaction models associated with this user.
   *
   * @return visible user transaction models associated with this user.
   * @throws SQLException if there are SQL-related errors.
   */
  public List<Transaction> getVisibleUserTransactions() throws SQLException {
    if (visibleUserTransactions == null) {
      Connection connection = DataService.getConnection();
      PreparedStatement statement = connection.prepareStatement(
          "SELECT * FROM " + Transaction.TABLE_NAME + " WHERE user_id=? AND show_user_history");

      statement.setLong(1, key);

      ResultSet result = statement.executeQuery();
      List<Transaction> data = new ArrayList<>();

      while (result.next()) {
        Transaction trans = new Transaction(result);
          trans.getDriver();
        data.add(trans);
      }

      visibleUserTransactions = data;
    }

    return visibleUserTransactions;
  }

  /**
   * Get visible driver transaction models associated with this user.
   *
   * @return visible driver transaction models associated with this user.
   * @throws SQLException if there are SQL-related errors.
   */
  public List<Transaction> getVisibleDriverTransactions() throws SQLException {
    if (visibleDriverTransactions == null) {
      Connection connection = DataService.getConnection();
      PreparedStatement statement = connection.prepareStatement(
          "SELECT * FROM " + Transaction.TABLE_NAME + " WHERE driver_id=? AND show_driver_history");

      statement.setLong(1, key);

      ResultSet result = statement.executeQuery();
      List<Transaction> data = new ArrayList<>();

      while (result.next()) {
          Transaction trans = new Transaction(result);
          trans.getUser();
        data.add(trans);
      }

      visibleDriverTransactions = data;
    }

    return visibleDriverTransactions;
  }

  /**
   * Get popularity model associated with this user.
   *
   * @return popularity model associated with this user.
   * @throws SQLException if there are SQL-related errors.
   */
  public Popularity getPopularity() throws SQLException {
    if (popularity == null) {
      popularity = Popularity.getByDriverId(id);
    }

    return popularity;
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
          "INSERT INTO " + TABLE_NAME
              + " (full_name, email, phone_number, is_driver, id) VALUES (?,?,?,?,?)",
              Statement.RETURN_GENERATED_KEYS);
    } else {
      statement = connection.prepareStatement(
          "UPDATE " + TABLE_NAME
              + " SET full_name=?, email=?, phone_number=?, is_driver=?, id=? WHERE id=?");
      statement.setLong(6, key);
    }

    statement.setString(1, fullName);
    statement.setString(2, email);
    statement.setString(3, phoneNumber);
    statement.setInt(4, isDriver ? 1 : 0);

    if (id == null) {
      statement.setNull(5, Types.NULL);
    } else {
      statement.setLong(5, id);
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
    key = null;
  }
}
