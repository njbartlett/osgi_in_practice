package org.osgi.book.concurrency;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class SafeConnectionCache implements ConnectionCache {

   private volatile Connection connection;

   public void initialise(DataSource dataSource) throws SQLException {
      connection = dataSource.getConnection();
   }
   
   public Connection getConnection() {
      return connection;
   }
}
