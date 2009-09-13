package org.osgi.book.reader.gui;

import org.osgi.book.reader.api.MailboxListener;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class MailboxListenerActivator implements BundleActivator {

   public void start(BundleContext context) throws Exception {
      MailboxListener listener = new MyMailboxListener();
      context.registerService(MailboxListener.class.getName(),
                              listener, null);
   }

   public void stop(BundleContext context) throws Exception {
      // No need to explicitly unregister the service
   }
}
