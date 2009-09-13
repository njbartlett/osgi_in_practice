package org.osgi.book.concurrency;

import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class ExecutorServiceActivator implements BundleActivator {

   private static final int POOL_SIZE = 20;

   private volatile ExecutorService threadPool;
   private volatile ServiceRegistration svcReg;

   public void start(BundleContext context) {
      threadPool = Executors.newFixedThreadPool(POOL_SIZE);

      Executor wrapper = new Executor() {
         public void execute(Runnable command) {
            threadPool.execute(command);
         }
      };

      Properties props = new Properties();
      props.put("kind", "fixed");
      props.put("poolSize", POOL_SIZE);
      svcReg = context.registerService(Executor.class.getName(),
            wrapper, props);
   }

   public void stop(BundleContext context) {
      svcReg.unregister();
      threadPool.shutdown();
   }
}