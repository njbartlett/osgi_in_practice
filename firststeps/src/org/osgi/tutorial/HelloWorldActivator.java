package org.osgi.tutorial;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class HelloWorldActivator implements BundleActivator {
   public void start(BundleContext context) throws Exception {
      System.out.println("Hello, World!");
   }

   public void stop(BundleContext context) throws Exception {
      System.out.println("Goodbye, World!");
   }
}
