package org.osgi.book.reader.dbmailbox;

import javax.sql.DataSource;

import org.osgi.book.reader.api.Mailbox;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class DbMailboxActivator implements BundleActivator {

   private BundleContext context;
   private ServiceTracker tracker;

   public void start(BundleContext context) throws Exception {
      this.context = context;

      tracker = new ServiceTracker(context, DataSource.class
            .getName(), new DSCustomizer());                      // 1
      tracker.open();                                             // 2
   }

   public void stop(BundleContext context) throws Exception {
      tracker.close();                                            // 3
   }

   private class DSCustomizer implements ServiceTrackerCustomizer {

      public Object addingService(ServiceReference ref) {
         DataSource ds = (DataSource) context.getService(ref);    // 4

         DbMailbox mbox = new DbMailbox(ds);
         ServiceRegistration registration = context.registerService(
               Mailbox.class.getName(), mbox, null);              // 5

         return registration;                                     // 6
      }

      public void modifiedService(ServiceReference ref,
            Object service) {
      }

      public void removedService(ServiceReference ref, Object service) {
         ServiceRegistration registration =
            (ServiceRegistration) service;                        // 7

         registration.unregister();                               // 8
         context.ungetService(ref);                               // 9
      }
   }
}
