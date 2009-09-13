package org.osgi.book.reader.tutorial;

import java.awt.EventQueue;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.osgi.book.reader.api.Mailbox;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

public class JLabelMailboxCountTracker extends ServiceTracker {

   private final JLabel label;
   private int count = 0;

   public JLabelMailboxCountTracker(JLabel label,
         BundleContext context) {
      super(context, Mailbox.class.getName(), null);
      this.label = label;
   }

   @Override
   public Object addingService(ServiceReference reference) {
      int displayCount;
      synchronized (this) {
         count++;
         displayCount = count;
      }
      updateDisplay(displayCount);
      return null;
   }

   @Override
   public void removedService(ServiceReference reference,
         Object service) {
      int displayCount;
      synchronized (this) {
         count--;
         displayCount = count;
      }
      updateDisplay(displayCount);
   }

   private void updateDisplay(final int displayCount) {
      Runnable action = new Runnable() {
         public void run() {
            label.setText("There are " + displayCount + " mailboxes");
         }
      };
      if(EventQueue.isDispatchThread()) {
         action.run();
      } else {
         SwingUtilities.invokeLater(action);
      }
   }
}
