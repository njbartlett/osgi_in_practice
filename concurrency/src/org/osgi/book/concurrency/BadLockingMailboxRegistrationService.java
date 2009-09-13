package org.osgi.book.concurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.osgi.book.reader.api.Mailbox;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class BadLockingMailboxRegistrationService implements
      MailboxRegistrationService {

   private final Map<String, ServiceRegistration> map
      = new HashMap<String, ServiceRegistration>();
   private final BundleContext context;

   public BadLockingMailboxRegistrationService(BundleContext context) {
      this.context = context;
   }

   // DO NOT DO THIS!
   public synchronized void registerMailbox(String name,
                                            Mailbox mailbox) {
      ServiceRegistration priorReg = map.get(name);
      if(priorReg != null) priorReg.unregister();

      Properties props = new Properties();
      props.put(Mailbox.NAME_PROPERTY, name);
      ServiceRegistration reg = context.registerService(
               Mailbox.class.getName(), mailbox, props);

      map.put(name, reg);
   }
}
