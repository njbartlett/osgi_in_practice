package org.osgi.tutorial;

import org.osgi.book.reader.api.Mailbox;
import org.osgi.book.reader.api.MailboxException;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class MessageCountActivator implements BundleActivator {

   private BundleContext context;

   public void start(BundleContext context) throws Exception {
      this.context = context;
      printMessageCount();
   }

   public void stop(BundleContext context) throws Exception {
   }

   private void printMessageCount() throws MailboxException {
      ServiceReference ref = context                             // 1
            .getServiceReference(Mailbox.class.getName());

      if (ref != null) {
         Mailbox mbox = (Mailbox) context.getService(ref);       // 2
         if (mbox != null) {
            try {
               int count = mbox.getAllMessages().length;         // 3
               System.out.println("There are " + count + "messages");
            } finally {
               context.ungetService(ref);                        // 4
            }
         }
      }
   }
}
