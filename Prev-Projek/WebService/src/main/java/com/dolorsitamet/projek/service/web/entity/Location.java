package com.dolorsitamet.projek.service.web.entity;

import com.dolorsitamet.projek.service.web.common.DataService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Location model class.
 */
@XmlRootElement(name = "Location")
public class Location {

  static final String TABLE_NAME = "locations";

  public Long key = null;

  @XmlElement(name = "id")
  public Long id;
  @XmlElement(name = "driver_id")
  public long driverId;
  @XmlElement(name = "name")
  public String name;

  /**
   * Create empty model.
   */
  public Location() {
  }

  /**
   * Create empty model forcibly associated with entity.
   *
   * @param key referenced key.
   */
  public Location(long key) {
    this.key = key;
  }

  /**
   * Create model from database result set.
   *
   * @param result database result set.
   * @throws SQLException if there are SQL-related errors.
   */
  Location(ResultSet result) throws SQLException {
    id = result.getLong("id");
    driverId = result.getLong("driver_id");
    name = result.getString("name");

    key = id;
  }

  /**
   * Get location model with specified ID.
   *
   * @param id entity ID.
   * @return location model with specified ID.
   * @throws SQLException if there are SQL-related errors.
   */
  public static Location getById(long id) throws SQLException {
    Connection connection = DataService.getConnection();
    PreparedStatement statement =
        connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE id=?");
    statement.setLong(1, id);

    ResultSet result = statement.executeQuery();
    return result.next() ? new Location(result) : null;
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
          "INSERT INTO " + TABLE_NAME + " (driver_id, name, id) VALUES (?,?,?)",
          Statement.RETURN_GENERATED_KEYS);
    } else {
      statement = connection.prepareStatement(
          "UPDATE " + TABLE_NAME + " SET driver_id=?, name=?, id=? WHERE id=?");
      statement.setLong(4, key);
    }

    statement.setLong(1, driverId);
    statement.setString(2, name);

    if (id == null) {
      statement.setNull(3, Types.NULL);
    } else {
      statement.setLong(3, id);
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
