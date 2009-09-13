package org.osgi.book.reader.gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.osgi.book.reader.api.Mailbox;
import org.osgi.book.reader.api.MailboxException;
import org.osgi.book.reader.api.MailboxListener;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class MailboxPanel extends JPanel {

   private final MailboxTableModel tableModel;
   private volatile ServiceRegistration svcReg;

   public MailboxPanel(Mailbox mbox) throws MailboxException {
      tableModel = new MailboxTableModel(mbox);
      JTable table = new JTable(tableModel);
      JScrollPane scrollPane = new JScrollPane(table);

      add(scrollPane);
   }
   
   public void registerListener(BundleContext context) {
      svcReg = context.registerService(
               MailboxListener.class.getName(), tableModel, null);
   }
   
   public void unregisterListener() {
      if(svcReg == null) throw new IllegalStateException();
      svcReg.unregister();
   }
}