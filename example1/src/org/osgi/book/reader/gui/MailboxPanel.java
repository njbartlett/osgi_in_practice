package org.osgi.book.reader.gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.osgi.book.reader.api.Mailbox;
import org.osgi.book.reader.api.MailboxException;

public class MailboxPanel extends JPanel {

   private final MailboxTableModel tableModel;

   public MailboxPanel(Mailbox mbox) throws MailboxException {
      tableModel = new MailboxTableModel(mbox);
      JTable table = new JTable(tableModel);
      JScrollPane scrollPane = new JScrollPane(table);

      add(scrollPane);
   }
}