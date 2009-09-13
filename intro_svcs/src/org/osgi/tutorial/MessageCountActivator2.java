package org.osgi.tutorial;

import org.osgi.book.reader.api.Mailbox;
import org.osgi.book.reader.api.MailboxException;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

public class MessageCountActivator2 implements BundleActivator {

   private ServiceTracker mboxTracker;

   public void start(BundleContext context) throws Exception {
      mboxTracker = new ServiceTracker(context, Mailbox.class    // 1
            .getName(), null);
      mboxTracker.open();                                        // 2
      printMessageCount();
   }

   public void stop(BundleContext context) throws Exception {
      mboxTracker.close();                                       // 3
   }
   
   private void printMessageCount() throws MailboxException {
      Mailbox mbox = (Mailbox) mboxTracker.getService();         // 4
      if (mbox != null) {
         int count = mbox.getAllMessages().length;               // 5
         System.out.println("There are " + count + "messages");
      }
   }
}

class Dummy {
   private ServiceTracker mboxTracker = null;
   
   private void printMessageCount(String message)
         throws InterruptedException, MailboxException {
      Mailbox mbox = (Mailbox) mboxTracker.waitForService(5000);
      if (mbox != null) {
         int count = mbox.getAllMessages().length;
         System.out.println("There are " + count + "messages");
      }
   }
}
