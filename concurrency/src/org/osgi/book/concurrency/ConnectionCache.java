package org.osgi.book.concurrency;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public interface ConnectionCache {
   void initialise(DataSource dataSource) throws SQLException;
   Connection getConnection();
}
