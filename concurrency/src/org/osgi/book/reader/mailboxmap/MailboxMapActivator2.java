package org.osgi.book.reader.mailboxmap;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.osgi.book.reader.api.Mailbox;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

public class MailboxMapActivator2 implements BundleActivator {

   private final Map<String, Mailbox> map =
         new HashMap<String, Mailbox>();
   private final ReadWriteLock mapLock = new ReentrantReadWriteLock();
   private volatile ServiceTracker tracker;

   public void start(BundleContext context) throws Exception {
      tracker = new MapTracker(context);
      tracker.open();
   }
   public void stop(BundleContext context) throws Exception {
      tracker.close();
   }
   public Mailbox getMailboxByName(String name) {
      try {
         mapLock.readLock().lock();
         return map.get(name);
      } finally {
         mapLock.readLock().unlock();
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
         try {
            mapLock.writeLock().lock();
            map.put(mboxName, mbox);
         } finally {
            mapLock.writeLock().unlock();
         }
         return mboxName;
      }
      public void removedService(ServiceReference reference,
                                 Object service) {
         String mboxName = (String) service;
         try {
            mapLock.writeLock().lock();
            map.remove(mboxName);
         } finally {
            mapLock.writeLock().unlock();
         }
      }
   }
}
