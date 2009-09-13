package org.osgi.book.reader.gui;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import org.osgi.book.reader.api.Mailbox;
import org.osgi.book.reader.api.MailboxException;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

public class ScannerMailboxTracker extends ServiceTracker {

   private final JTabbedPane tabbedPane;

   public ScannerMailboxTracker(BundleContext ctx,
         JTabbedPane tabbedPane) {
      super(ctx, Mailbox.class.getName(), null);
      this.tabbedPane = tabbedPane;
   }

   // ...

   @Override
   public Object addingService(ServiceReference reference) {
      final String mboxName =
         (String) reference.getProperty(Mailbox.NAME_PROPERTY);
      final Mailbox mbox = (Mailbox) context.getService(reference);

      Callable<MailboxPanel> callable = new Callable<MailboxPanel>() {
         public MailboxPanel call() {
            MailboxPanel panel;
            try {
               panel = new MailboxPanel(mbox);
               panel.registerListener(context);
               String title = (mboxName != null) ?
                     mboxName : "<unknown>";
               tabbedPane.addTab(title, panel);
            } catch (MailboxException e) {
               JOptionPane.showMessageDialog(tabbedPane, e.getMessage(),
                           "Error", JOptionPane.ERROR_MESSAGE);
               panel = null;
            }
            return panel;
         }
      };
      FutureTask<MailboxPanel> future =
         new FutureTask<MailboxPanel>(callable);
      SwingUtilities.invokeLater(future);

      return future;
   }

   @Override
   public void removedService(ServiceReference reference, Object svc) {
      
      @SuppressWarnings("unchecked")
      final Future<MailboxPanel> panelRef = (Future<MailboxPanel>) svc;
      
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            try {
               MailboxPanel panel = panelRef.get();
               if (panel != null) {
                  panel.unregisterListener();
                  tabbedPane.remove(panel);
               }
            } catch (ExecutionException e) {
               // The MailboxPanel was not successfully created
            } catch (InterruptedException e) {
               // Restore interruption status
               Thread.currentThread().interrupt();
            }
         }
      });

      context.ungetService(reference);
   }
}
