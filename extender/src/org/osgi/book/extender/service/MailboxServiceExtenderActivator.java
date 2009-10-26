package org.osgi.book.extender.service;

import org.osgi.book.utils.LogTracker;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class MailboxServiceExtenderActivator
      implements BundleActivator {

   private volatile LogTracker logTracker;
   private volatile MailboxServiceExtender extender;

   public void start(BundleContext context) throws Exception {
      logTracker = new LogTracker(context);
      logTracker.open();
      
      extender = new MailboxServiceExtender(context, logTracker);
      extender.open();
   }

   public void stop(BundleContext context) throws Exception {
      extender.close();
      logTracker.close();
   }

}
