package org.osgi.book.log;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class LogServiceFactoryActivator implements BundleActivator {

   public void start(BundleContext context) throws Exception {
      context.registerService(Log.class.getName(),
            new LogServiceFactory(), null);
   }

   public void stop(BundleContext context) throws Exception {
   }
}
