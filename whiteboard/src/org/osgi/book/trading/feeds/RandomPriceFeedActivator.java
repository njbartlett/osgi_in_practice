package org.osgi.book.trading.feeds;

import org.osgi.book.utils.EventAdminTracker;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class RandomPriceFeedActivator implements BundleActivator {

   private EventAdminTracker evtAdmTracker;
   private Thread thread;

   public void start(BundleContext context) throws Exception {
      evtAdmTracker = new EventAdminTracker(context);
      evtAdmTracker.open();

      thread = new Thread(new RandomPriceFeed(evtAdmTracker));
      thread.start();
   }

   public void stop(BundleContext context) throws Exception {
      thread.interrupt();
      evtAdmTracker.close();
   }
}