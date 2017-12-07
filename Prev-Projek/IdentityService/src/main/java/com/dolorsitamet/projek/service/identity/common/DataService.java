package com.dolorsitamet.projek.service.identity.common;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataService {

  private static final String SOURCE_NAME = "db/identity";

  private static DataSource source;
  private static Connection connection;

  static {
    try {
      InitialContext context = new InitialContext();
      source = (DataSource) context.lookup(SOURCE_NAME);
    } catch (NamingException e) {
      throw new RuntimeException(e);
    }
  }

  public static Connection getConnection() throws SQLException {
    if (connection == null) {
      connection = source.getConnection();
    }

    return connection;
  }
}
