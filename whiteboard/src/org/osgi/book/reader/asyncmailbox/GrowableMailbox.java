package org.osgi.book.reader.asyncmailbox;

import org.osgi.book.reader.api.Mailbox;
import org.osgi.book.reader.api.MailboxListener;
import org.osgi.book.reader.api.Message;
import org.osgi.book.reader.fixedmailbox.FixedMailbox;
import org.osgi.book.reader.fixedmailbox.StringMessage;
import org.osgi.book.utils.Visitor;
import org.osgi.book.utils.WhiteboardHelper;

public class GrowableMailbox extends FixedMailbox {

   private final WhiteboardHelper<MailboxListener> whiteboard;
   private final String mailboxName;

   public GrowableMailbox(WhiteboardHelper<MailboxListener> wb,
         String mailboxName) {
      this.whiteboard = wb;
      this.mailboxName = mailboxName;
   }

   protected void addMessage(String subject, String text) {
      final int newMessageId;

      synchronized (this) {
         newMessageId = messages.size();
         Message newMessage = new StringMessage(newMessageId,
               subject, text);
         messages.add(newMessage);
      }

      final long[] newMessageIds = new long[] { newMessageId };
      final Mailbox source = this;
      Visitor<MailboxListener> v = new Visitor<MailboxListener>() {
         public void visit(MailboxListener l) {
            l.messagesArrived(mailboxName, source, newMessageIds);
         }
      };
      whiteboard.accept(v);
   }
}
