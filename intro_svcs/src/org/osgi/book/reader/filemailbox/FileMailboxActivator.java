package org.osgi.book.reader.filemailbox;

import java.io.File;
import java.util.Properties;

import org.osgi.book.reader.api.Mailbox;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class FileMailboxActivator implements BundleActivator {

   private Thread thread;

   public void start(BundleContext context) throws Exception {
      File file = new File(System.getProperty("user.home")
            + System.getProperty("file.separator") + "messages.txt");
      RegistrationRunnable runnable = new RegistrationRunnable(
            context, file, null);
      thread = new Thread(runnable);
      thread.start();
   }

   public void stop(BundleContext context) throws Exception {
      thread.interrupt();
   }
}

class RegistrationRunnable implements Runnable {

   private final BundleContext context;
   private final File file;
   private final Properties props;

   public RegistrationRunnable(BundleContext context, File file,
         Properties props) {
      this.context = context;
      this.file = file;
      this.props = props;
   }

   public void run() {
      ServiceRegistration registration = null;
      try {
         while (!Thread.currentThread().isInterrupted()) {
            if (file.exists()) {
               if (registration == null) {
                  registration = context.registerService(        // 1
                        Mailbox.class.getName(),
                        new FileMailbox(file), props);
               }
            } else {
               if (registration != null) {
                  registration.unregister();
                  registration = null;
               }
            }
            Thread.sleep(5000);
         }
      } catch (InterruptedException e) {
         // Allow thread to exit
      }
   }
}
