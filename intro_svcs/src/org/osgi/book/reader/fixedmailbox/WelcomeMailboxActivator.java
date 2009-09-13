package org.osgi.book.reader.fixedmailbox;

import org.osgi.book.reader.api.Mailbox;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class WelcomeMailboxActivator implements BundleActivator {

   public void start(BundleContext context) throws Exception {
      Mailbox mbox = new FixedMailbox();
      context.registerService(Mailbox.class.getName(), mbox, null); //1
   }

   public void stop(BundleContext context) throws Exception {
   }
}