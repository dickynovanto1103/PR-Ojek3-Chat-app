package com.dolorsitamet.projek.service.web.entity;

import com.dolorsitamet.projek.service.web.common.DataService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Popularity model class.
 */
@XmlRootElement(name = "Popularity")
public class Popularity {

  private static final String TABLE_NAME = "popularities";

  @XmlElement(name = "driver_id")
  public long driverId;
  @XmlElement(name = "rating")
  public float rating;
  @XmlElement(name = "vote_count")
  public long voteCount;

  /**
   * Create empty model.
   */
  public Popularity() {
  }

  /**
   * Create model from database result set.
   *
   * @param result database result set.
   * @throws SQLException if there are SQL-related errors.
   */
  private Popularity(ResultSet result) throws SQLException {
    driverId = result.getLong("driver_id");
    rating = result.getFloat("rating");
    voteCount = result.getLong("vote_count");
  }

  /**
   * Get popularity model with specified driver ID.
   *
   * @param driverId driver ID.
   * @return transaction model with specified ID.
   * @throws SQLException if there are SQL-related errors.
   */
  static Popularity getByDriverId(long driverId) throws SQLException {
    Connection connection = DataService.getConnection();
    PreparedStatement statement =
        connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE driver_id=?");

    statement.setLong(1, driverId);

    ResultSet result = statement.executeQuery();
    return result.next() ? new Popularity(result) : null;
  }
}
