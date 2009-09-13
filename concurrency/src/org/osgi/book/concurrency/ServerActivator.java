package org.osgi.book.concurrency;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class ServerActivator implements BundleActivator {

   private HelloServer serverThread = new HelloServer();

   public void start(BundleContext context) throws Exception {
      serverThread.start();
   }

   public void stop(BundleContext context) throws Exception {
      serverThread.interrupt();
   }

}

class HelloServer extends Thread {

   private static final int PORT = 9876;

   private volatile ServerSocket socket = null;

   public void interrupt() {
      super.interrupt();
      try {
         if (socket != null) {
            socket.close();
         }
      } catch (IOException e) {
         // Ignore
      }
   }

   public void run() {
      try {
         socket = new ServerSocket(PORT);

         while (!Thread.currentThread().isInterrupted()) {
            System.out.println("Accepting connections...");
            Socket clientSock = socket.accept();
            System.out.println("Client connected.");
            PrintStream out = new PrintStream(clientSock
                  .getOutputStream());
            out.println("Hello, World!");
            out.flush();
            out.close();
         }
      } catch (IOException e) {
         System.out.println("Server thread terminated.");
      }
   }

}
