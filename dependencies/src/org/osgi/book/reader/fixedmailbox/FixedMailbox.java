package org.osgi.book.reader.fixedmailbox;

import java.util.ArrayList;
import java.util.List;

import org.osgi.book.reader.api.Mailbox;
import org.osgi.book.reader.api.MailboxException;
import org.osgi.book.reader.api.Message;

public class FixedMailbox implements Mailbox {

   protected final List<Message> messages;

   public FixedMailbox() {
      messages = new ArrayList<Message>(2);
      messages.add(new StringMessage(0, "Hello", "Welcome to OSGi"));
      messages.add(new StringMessage(1, "Getting Started",
            "To learn about OSGi, read my book."));
   }

   public synchronized long[] getAllMessages() {
      long[] ids = new long[messages.size()];
      for (int i = 0; i < ids.length; i++) {
         ids[i] = i;
      }
      return ids;
   }

   public synchronized Message[] getMessages(long[] ids)
         throws MailboxException {
      Message[] result = new Message[ids.length];
      for (int i = 0; i < ids.length; i++) {
         long id = ids[i];
         if (id < 0 || id >= messages.size()) {
            throw new MailboxException("Invalid message ID: " + id);
         }
         result[i] = messages.get((int) id);
      }
      return result;
   }

   public synchronized long[] getMessagesSince(long id)
         throws MailboxException {
      int first = (int) (id + 1);
      if (first < 0) {
         throw new MailboxException("Invalid message ID: " + first);
      }
      int length = Math.max(0, messages.size() - first);
      long[] ids = new long[length];
      for (int i = 0; i < length; i++) {
         ids[i] = i + first;
      }
      return ids;
   }

   public void markRead(boolean read, long[] ids) {
      // Ignore
   }
}
