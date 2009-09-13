package org.osgi.book.concurrency;

import org.osgi.book.utils.LogTracker;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.SynchronousBundleListener;
import org.osgi.service.log.LogService;

public class ReallySafeBundleActivator implements BundleActivator,
      SynchronousBundleListener {

   private LogTracker log;

   public void start(BundleContext context) throws Exception {
      log = new LogTracker(context);
      log.open();
      
      context.addBundleListener(this);
   }

   public void bundleChanged(BundleEvent event) {
      if(BundleEvent.INSTALLED == event.getType()) {
         log.log(LogService.LOG_INFO, "Bundle installed");
      } else if(BundleEvent.UNINSTALLED == event.getType()) {
         log.log(LogService.LOG_INFO, "Bundle removed");
      }
   }

   public void stop(BundleContext context) throws Exception {
      context.removeBundleListener(this);
      log.close();
   }
}
