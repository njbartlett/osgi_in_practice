package org.osgi.book.utils;

import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

public class WhiteboardHelper<T> extends ServiceTracker {

   public WhiteboardHelper(BundleContext context, Class<T> svcClass) {
      super(context, svcClass.getName(), null);
   }

   public void accept(Visitor<? super T> visitor) {
      Object[] services = getServices();
      if (services != null) {
         for (Object serviceObj : services) {
            @SuppressWarnings("unchecked")
            T service = (T) serviceObj;

            visitor.visit(service);
         }
      }
   }
}