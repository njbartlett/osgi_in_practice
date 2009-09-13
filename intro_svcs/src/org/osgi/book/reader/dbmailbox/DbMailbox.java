package org.osgi.book.reader.dbmailbox;

import javax.sql.DataSource;

import org.osgi.book.reader.api.Mailbox;
import org.osgi.book.reader.api.Message;

/**
 * Warning: Empty stub implementation
 */
public class DbMailbox implements Mailbox {

   private static final long[] EMPTY = new long[0];

   public DbMailbox(DataSource db) {}
   public long[] getAllMessages() { return EMPTY; }
   public Message[] getMessages(long[] ids) {
      return new Message[0];
   }
   public long[] getMessagesSince(long id) { return EMPTY; }
   public void markRead(boolean read, long[] ids) { }
}
