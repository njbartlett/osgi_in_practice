package org.osgi.book.extender.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.book.reader.api.Mailbox;
import org.osgi.framework.*;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.BundleTracker;

public class MailboxServiceExtender extends BundleTracker {

   private static final String SVC_HEADER = "Mailbox-ServiceClass";
   private final Map<String, ServiceRegistration> registrations
      = new ConcurrentHashMap<String, ServiceRegistration>();
   private final LogService log;

   public MailboxServiceExtender(BundleContext ctx, LogService log) {
      super(ctx, Bundle.ACTIVE, null);
      this.log = log;
   }
   
   @Override
   public Object addingBundle(Bundle bundle, BundleEvent ev) {
      Bundle result = null;
      String className = (String) bundle.getHeaders().get(SVC_HEADER);
      if (className != null) {
         try {
            Class<?> svcClass = bundle.loadClass(className);
            if (!Mailbox.class.isAssignableFrom(svcClass)) {
               log.log(LogService.LOG_ERROR,
                     "Declared class is not an instance of Mailbox");
            } else {
               Object instance = svcClass.newInstance();
               ServiceRegistration reg = bundle.getBundleContext()
                     .registerService(Mailbox.class.getName(), instance,
                           null);
               registrations.put(bundle.getLocation(), reg);
               result = bundle;
            }
         } catch (ClassNotFoundException e) {
            log.log(LogService.LOG_ERROR, "Error creating service", e);
         } catch (InstantiationException e) {
            log.log(LogService.LOG_ERROR, "Error creating service", e);
         } catch (IllegalAccessException e) {
            log.log(LogService.LOG_ERROR, "Error creating service", e);
         }
      }
      return result;
   }

   @Override
   public void removedBundle(Bundle bundle, BundleEvent ev, Object ebj) {
      ServiceRegistration reg;
      reg = registrations.remove(bundle.getLocation());
      if (reg != null) reg.unregister();
   }
}