package org.osgi.book.concurrency;

import org.osgi.book.reader.api.Mailbox;

public interface MailboxRegistrationService {
   void registerMailbox(String name, Mailbox mailbox);
}
