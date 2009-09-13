package org.osgi.tutorial;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;

public class BundleCounterActivator implements BundleActivator,
      BundleListener {

   private BundleContext context;

   public void start(BundleContext context) throws Exception {
      this.context = context;

      context.addBundleListener(this); //1
      printBundleCount();              //2
   }

   public void stop(BundleContext context) throws Exception {
      context.removeBundleListener(this);
   }

   public void bundleChanged(BundleEvent event) {
      switch (event.getType()) {
      case BundleEvent.INSTALLED:
         System.out.println("Bundle installed");
         printBundleCount();
         break;
      case BundleEvent.UNINSTALLED:
         System.out.println("Bundle uninstalled");
         printBundleCount();
         break;
      }
   }

   private void printBundleCount() {
      int count = context.getBundles().length;
      System.out.println("There are currently " + count + " bundles");
   }
}
