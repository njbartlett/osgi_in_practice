package org.osgi.tutorial;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class HeartbeatActivator implements BundleActivator {

   private Thread thread;

   public void start(BundleContext context) throws Exception {
      thread = new Thread(new Heartbeat());
      thread.start();
   }

   public void stop(BundleContext context) throws Exception {
      thread.interrupt();
   }
}

class Heartbeat implements Runnable {

   public void run() {
      try {
         while (!Thread.currentThread().isInterrupted()) {
            Thread.sleep(5000);
            System.out.println("I'm still here.");
         }
      } catch (InterruptedException e) {
         System.out.println("I'm going now.");
      }
   }
}
