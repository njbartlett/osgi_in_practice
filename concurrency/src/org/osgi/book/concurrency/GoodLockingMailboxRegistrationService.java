package org.osgi.book.concurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.osgi.book.reader.api.Mailbox;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class GoodLockingMailboxRegistrationService implements
      MailboxRegistrationService {

   private final Map<String, ServiceRegistration> map
      = new HashMap<String, ServiceRegistration>();

   private final BundleContext context;

   public GoodLockingMailboxRegistrationService(BundleContext context) {
      this.context = context;
   }

   public void registerMailbox(String name, Mailbox mailbox) {
      Properties props = new Properties();
      props.put(Mailbox.NAME_PROPERTY, name);
      ServiceRegistration reg = context.registerService(
               Mailbox.class.getName(), mailbox, props);

      ServiceRegistration priorReg;
      synchronized (map) {
         priorReg = map.put(name, reg);
      }

      if (priorReg != null) {
         priorReg.unregister();
      }
   }
}
