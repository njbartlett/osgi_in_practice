package org.osgi.book.concurrency;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class UnsafeConnectionCache implements ConnectionCache {

   private Connection connection;

   public void initialise(DataSource dataSource) throws SQLException {
      connection = dataSource.getConnection();
   }
   
   public Connection getConnection() {
      return connection;
   }
}
