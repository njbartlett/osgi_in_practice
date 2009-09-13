package org.osgi.book.reader.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import org.osgi.framework.BundleContext;

public class ScannerFrame extends JFrame {

   private JTabbedPane tabbedPane;
   private ScannerMailboxTracker tracker;

   public ScannerFrame() {
      super("Mailbox Scanner");

      tabbedPane = new JTabbedPane();
      tabbedPane.addTab("Mailboxes", createIntroPanel());
      tabbedPane.setPreferredSize(new Dimension(400, 400));

      getContentPane().add(tabbedPane, BorderLayout.CENTER);
   }
   

   private Component createIntroPanel() {
      JLabel label = new JLabel("Select a Mailbox");
      label.setHorizontalAlignment(SwingConstants.CENTER);
      return label;
   }


   protected void openTracking(BundleContext context) {
      tracker = new ScannerMailboxTracker(context, tabbedPane);
      tracker.open();
   }

   protected void closeTracking() {
      tracker.close();
   }
}
