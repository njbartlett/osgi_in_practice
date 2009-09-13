package org.osgi.book.reader.fixedmailbox;

import java.util.Properties;

import org.osgi.book.reader.api.Mailbox;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class WelcomeMailboxActivator2 implements BundleActivator {

   public void start(BundleContext context) throws Exception {
      Mailbox mbox = new FixedMailbox();

      Properties props = new Properties();
      props.put(Mailbox.NAME_PROPERTY, "welcome");
      context.registerService(Mailbox.class.getName(), mbox, props);
   }

   public void stop(BundleContext context) throws Exception {
   }

}
