package org.osgi.book.utils.sample;

import org.osgi.book.utils.LogTracker;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class DbConnectionActivator implements BundleActivator {

   private LogTracker log;
   private DbConnection dbconn;

   public void start(BundleContext context) throws Exception {
      log = new LogTracker(context);
      log.open();

      dbconn = new DbConnection(log);
   }

   public void stop(BundleContext context) throws Exception {
      dbconn.disconnect();
      log.close();
   }
}
