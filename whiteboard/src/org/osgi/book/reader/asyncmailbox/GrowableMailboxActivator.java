package org.osgi.book.reader.asyncmailbox;

import java.util.Date;
import java.util.Properties;

import org.osgi.book.reader.api.Mailbox;
import org.osgi.book.reader.api.MailboxListener;
import org.osgi.book.utils.WhiteboardHelper;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class GrowableMailboxActivator implements BundleActivator {

   private static final String MAILBOX_NAME = "growing";
   private WhiteboardHelper<MailboxListener> whiteboard;
   private GrowableMailbox mailbox;
   private Thread messageAdderThread;
   private ServiceRegistration svcReg;

   public void start(BundleContext context) throws Exception {
      whiteboard = new WhiteboardHelper<MailboxListener>(context,
                                          MailboxListener.class);
      whiteboard.open(true);
    
      mailbox = new GrowableMailbox(whiteboard, MAILBOX_NAME);
      messageAdderThread = new Thread(new MessageAdder());
      messageAdderThread.start();
      
      Properties props = new Properties();
      props.put(Mailbox.NAME_PROPERTY, MAILBOX_NAME);
      svcReg = context.registerService(Mailbox.class.getName(),
                                       mailbox, props);
   }

   public void stop(BundleContext context) throws Exception {
      svcReg.unregister();
      messageAdderThread.interrupt();
      whiteboard.close();
   }

   private class MessageAdder implements Runnable {
      public void run() {
         try {
            while (!Thread.currentThread().isInterrupted()) {
               Thread.sleep(5000);
               mailbox.addMessage("Message added at " + new Date(),
                     "Hello again");
            }
         } catch (InterruptedException e) {
            // Exit quietly
         }
      }
   }
}
