package org.osgi.book.reader.api;

public interface ObservableMailbox extends Mailbox {
   void addMailboxListener(MailboxListener listener);
   void removeMailboxListener(MailboxListener listener);
}
