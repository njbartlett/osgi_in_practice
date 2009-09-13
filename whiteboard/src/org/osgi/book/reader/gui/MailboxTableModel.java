package org.osgi.book.reader.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import org.osgi.book.reader.api.Mailbox;
import org.osgi.book.reader.api.MailboxException;
import org.osgi.book.reader.api.MailboxListener;
import org.osgi.book.reader.api.Message;

public class MailboxTableModel extends AbstractTableModel implements
      MailboxListener {

   private static final String ERROR = "ERROR";

   private final Mailbox mailbox;
   private final List<Message> messages;

   public MailboxTableModel(Mailbox mailbox) throws MailboxException {
      this.mailbox = mailbox;
      long[] messageIds = mailbox.getAllMessages();
      messages = new ArrayList<Message>(messageIds.length);
      Message[] messageArray = mailbox.getMessages(messageIds);
      for (Message message : messageArray) {
         messages.add(message);
      }
   }

   public synchronized int getRowCount() {
      return messages.size();
   }

   public int getColumnCount() {
      return 2;
   }

   @Override
   public String getColumnName(int column) {
      switch (column) {
      case 0:
         return "ID";
      case 1:
         return "Subject";
      }
      return ERROR;
   }

   public synchronized Object getValueAt(int row, int column) {
      Message message = messages.get(row);
      switch (column) {
      case 0:
         return Long.toString(message.getId());
      case 1:
         return message.getSummary();
      }
      return ERROR;
   }

   public void messagesArrived(String mailboxNam, Mailbox mailbox,
         long[] ids) {
      if (mailbox != this.mailbox) {
         // Ignore events for other mailboxes
         return;
      }

      List<Message> newMessages;
      try {
         newMessages = Arrays.asList(mailbox.getMessages(ids));
      } catch (MailboxException e) {
         newMessages = Collections.emptyList();
      }

      final int firstNew, lastNew;
      synchronized (this) {
         firstNew = messages.size(); // Index of the first new row
         messages.addAll(newMessages);
         lastNew = messages.size() - 1; // Index of the last new row
      }

      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            fireTableRowsInserted(firstNew, lastNew);
         }
      });
   }
}