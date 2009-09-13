package org.osgi.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.SynchronousBundleListener;

public abstract class BundleTracker {

   private final BundleContext context;
   private final Map<String, Bundle> trackedSet
      = new HashMap<String, Bundle>();

   private final BundleListener listener =
      new SynchronousBundleListener() {
      public void bundleChanged(BundleEvent event) {
         if (BundleEvent.STARTED == event.getType()) {
            internalAdd(event.getBundle());
         } else if (BundleEvent.STOPPING == event.getType()) {
            internalRemove(event.getBundle());
         }
      }
   };

   private boolean isOpen = false;

   public BundleTracker(BundleContext context) {
      this.context = context;
   }

   /**
    * Open this <code>BundleTracker</code> and begin tracking
    */
   public final void open() {
      synchronized (this) {
         if (isOpen) {
            return;
         } else {
            isOpen = true;
         }
      }

      context.addBundleListener(listener);

      Bundle[] bundles = context.getBundles();
      for (Bundle bundle : bundles) {
         if (Bundle.ACTIVE == bundle.getState()) {
            internalAdd(bundle);
         }
      }
   }

   /**
    * Close this <code>BundleTracker</code> and stop tracking
    */
   public final void close() {
      synchronized (this) {
         if (!isOpen) {
            return;
         } else {
            isOpen = false;
         }
      }
      context.removeBundleListener(listener);

      Bundle[] bundles;
      synchronized (trackedSet) {
         bundles = trackedSet.values().toArray(
                                       new Bundle[trackedSet.size()]);
      }

      for (Bundle bundle : bundles) {
         internalRemove(bundle);
      }
   }

   /**
    * Return a snapshot of the currently tracked bundles.
    */
   public Bundle[] getBundles() {
      synchronized (trackedSet) {
         Collection<Bundle> bundles = trackedSet.values();
         Bundle[] result = bundles.toArray(
                                   new Bundle[bundles.size()]);
         return result;
      }
   }

   /**
    * Called when a bundle is being added to the
    * <code>BundleTracker</code>. This method does nothing, it is
    * expected to be overridden by concrete subclasses.
    * 
    * @param bundle
    */
   protected void addingBundle(Bundle bundle) {
   }

   /**
    * Called when a bundle is being removed from the
    * <code>BundleTracker</code> This method does nothing, it is
    * expected to be overridden by concrete subclasses.
    * 
    * @param bundle
    */
   protected void removedBundle(Bundle bundle) {
   }

   private void internalAdd(Bundle bundle) {
      Bundle prior;
      synchronized (trackedSet) {
         prior = trackedSet.put(bundle.getLocation(), bundle);
      }
      if (prior == null) {
         addingBundle(bundle);
      }
   }

   private void internalRemove(Bundle bundle) {
      Bundle removed;
      synchronized (trackedSet) {
         removed = trackedSet.remove(bundle.getLocation());
      }
      if (removed != null) {
         removedBundle(removed);
      }
   }
}