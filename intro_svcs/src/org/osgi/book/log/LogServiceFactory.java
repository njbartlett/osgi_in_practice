package org.osgi.book.log;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceRegistration;

class LogImpl implements Log {

   private String sourceBundleName;

   public LogImpl(Bundle bundle) {
      this.sourceBundleName = bundle.getSymbolicName();
   }

   public void log(String message) {
      System.out.println(sourceBundleName + ": " + message);
   }
}

public class LogServiceFactory implements ServiceFactory {

   public Object getService(Bundle bundle,
         ServiceRegistration registration) {
      return new LogImpl(bundle);
   }

   public void ungetService(Bundle bundle,
         ServiceRegistration registration, Object service) {
      // No special clean-up required
   }
}
