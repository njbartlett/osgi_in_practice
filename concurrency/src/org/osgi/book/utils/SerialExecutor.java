package org.osgi.book.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import org.osgi.service.log.LogService;

public class SerialExecutor implements Executor {
   private final List<Runnable> queue = new ArrayList<Runnable>();
   private final ThreadLocal<Integer> taskCount;
   private final LogService log;

   public SerialExecutor() {
      this(null);
   }

   public SerialExecutor(LogService log) {
      this.log = log;
      taskCount = new ThreadLocal<Integer>() {
         protected Integer initialValue() {
            return 0;
         }
      };
   }

   public void execute(Runnable command) {
      Runnable next = null;
      int worked = 0;

      // If the queue is empty, I am the master and my next task is to
      // execute my own work. If the queue is non-empty, then I simply
      // add my task to the queue and return.
      synchronized (this) {
         next = queue.isEmpty() ? command : null;
         queue.add(command);
      }

      while (next != null) {
         // Do the work!
         try {
            next.run();
            worked++;
         } catch (Exception e) {
            logError("Error processing task", e);
         }

         // Get more work if it exists on the queue
         synchronized (this) {
            queue.remove(0); // Head element is the one just processed
            next = queue.isEmpty() ? null : queue.get(0);
         }

         taskCount.set(worked);
      }
   }

   /**
    * Returns the number of tasks executed by the last call to
    * <code>execute</code> from the calling thread.
    */
   public int getLastTaskCount() {
      return taskCount.get();
   }

   private void logError(String message, Exception e) {
      if (log != null) {
         log.log(LogService.LOG_ERROR, message, e);
      }
   }
}
