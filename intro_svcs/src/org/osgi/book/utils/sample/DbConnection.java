package org.osgi.book.utils.sample;

import org.osgi.service.log.LogService;

public class DbConnection {

   private final LogService log;

   public DbConnection(LogService log) {
      this.log = log;
      log.log(LogService.LOG_INFO, "Opening connection");
      // ...
   }

   public void disconnect() {
      log.log(LogService.LOG_INFO, "Disconnecting");
      // ...
   }
}
