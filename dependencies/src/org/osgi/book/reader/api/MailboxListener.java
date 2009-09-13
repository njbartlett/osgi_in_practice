package org.osgi.book.reader.api;

public interface MailboxListener {
   void messagesArrived(String mailboxName, Mailbox mailbox, long[] ids);
}