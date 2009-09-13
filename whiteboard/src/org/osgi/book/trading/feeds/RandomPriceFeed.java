package org.osgi.book.trading.feeds;

import java.util.Properties;
import java.util.Random;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

public class RandomPriceFeed implements Runnable {

   private static final String TOPIC = "PRICES/STOCKS/NASDAQ/MSFT";

   private final EventAdmin eventAdmin;

   public RandomPriceFeed(EventAdmin eventAdmin) {
      this.eventAdmin = eventAdmin;
   }

   public void run() {
      double price = 25;
      Random random = new Random();

      try {
         while (!Thread.currentThread().isInterrupted()) {
            // Create  and send the event
            Properties props = new Properties();
            props.put("symbol", "MSFT");
            props.put("time", System.currentTimeMillis());
            props.put("price", price);
            eventAdmin.sendEvent(new Event(TOPIC, props));

            // Sleep 100ms
            Thread.sleep(100);

            // Randomly adjust price by upto 1.0, + or -
            double nextPrice = random.nextBoolean()
                             ? price + random.nextDouble()
                             : price - random.nextDouble();
            price = Math.max(0, nextPrice);
         }
      } catch (InterruptedException e) {
         // Exit quietly
      }
   }
}
