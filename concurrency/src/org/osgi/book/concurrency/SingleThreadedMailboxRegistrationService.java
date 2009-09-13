package org.osgi.book.concurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.osgi.book.reader.api.Mailbox;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class SingleThreadedMailboxRegistrationService implements
      MailboxRegistrationService {

   private final Map<String, ServiceRegistration> map
      = new HashMap<String, ServiceRegistration>();
   private final BundleContext context;

   private final ExecutorService executor =
         Executors.newSingleThreadExecutor();

   public SingleThreadedMailboxRegistrationService(
         BundleContext context) {
      this.context = context;
   }

   public void registerMailbox(final String name,
                               final Mailbox mailbox) {
      Runnable task = new Runnable() {
         public void run() {
            ServiceRegistration priorReg = map.get(name);
            priorReg.unregister();

            Properties props = new Properties();
            props.put(Mailbox.NAME_PROPERTY, name);
            ServiceRegistration reg = context.registerService(
                  Mailbox.class.getName(), mailbox, props);
            map.put(name, reg);
         }
      };
      executor.execute(task);
   }

   public void cleanup() {
      executor.shutdown();
   }
}