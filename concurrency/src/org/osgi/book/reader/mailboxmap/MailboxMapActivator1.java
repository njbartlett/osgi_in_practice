package org.osgi.book.reader.mailboxmap;

import java.util.HashMap;
import java.util.Map;

import org.osgi.book.reader.api.Mailbox;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

public class MailboxMapActivator1 implements BundleActivator {

   private final Map map = new HashMap();
   private ServiceTracker tracker;

   public void start(BundleContext context) throws Exception {
      tracker = new MapTracker(context);
      tracker.open();
   }
   public void stop(BundleContext context) throws Exception {
      tracker.close();
   }

   public Mailbox getMailboxByName(String name) {
      synchronized (map) {
         return (Mailbox) map.get(name);
      }
   }

   private class MapTracker extends ServiceTracker {

      public MapTracker(BundleContext context) {
         super(context, Mailbox.class.getName(), null);
      }

      public Object addingService(ServiceReference reference) {
         String mboxName = (String) reference
               .getProperty(Mailbox.NAME_PROPERTY);
         Mailbox mbox = (Mailbox) context.getService(reference);
         synchronized (map) {
            map.put(mboxName, mbox);
         }
         return mboxName;
      }

      public void removedService(ServiceReference reference,
            Object service) {
         String mboxName = (String) service;
         synchronized (map) {
            map.remove(mboxName);
         }
      }
   }
}
