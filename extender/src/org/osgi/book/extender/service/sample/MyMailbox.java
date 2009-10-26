package org.osgi.book.extender.service.sample;

import org.osgi.book.reader.api.*;

public class MyMailbox implements Mailbox {

   public long[] getAllMessages() {
      return new long[0];
   }
   public Message[] getMessages(long[] ids) {
      return new Message[0];
   }
   public long[] getMessagesSince(long id) {
      return new long[0];
   }
   public void markRead(boolean read, long[] ids) {
   }
}