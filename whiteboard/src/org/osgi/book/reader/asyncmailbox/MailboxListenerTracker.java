package org.osgi.book.reader.asyncmailbox;

import org.osgi.book.reader.api.Mailbox;
import org.osgi.book.reader.api.MailboxListener;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

public class MailboxListenerTracker extends ServiceTracker {

   public MailboxListenerTracker(BundleContext context) {
      super(context, MailboxListener.class.getName(), null);
   }

   public void fireMessagesArrived(String mailboxName,
         Mailbox mailbox, long[] ids) {
      Object[] services = getServices();
      for (int i = 0; services != null && i < services.length; i++) {
         ((MailboxListener) services[i]).messagesArrived(mailboxName,
               mailbox, ids);
      }
   }
}