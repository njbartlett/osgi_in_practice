package org.osgi.tutorial;

import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

public class StockTickerActivator implements BundleActivator {
   
   private static final String STOCKS_TOPIC = "PRICES/STOCKS/*";

   public void start(BundleContext context) throws Exception {
      EventHandler handler = new EventHandler() {
         public void handleEvent(Event event) {
            String symbol = (String) event.getProperty("symbol");
            Double price = (Double) event.getProperty("price");
            
            System.out.println("The price of " + symbol
                  + " is now " + price);
         }
      };

      Properties props = new Properties();
      props.put(EventConstants.EVENT_TOPIC, STOCKS_TOPIC);
      props.put(EventConstants.EVENT_FILTER, "(price>=20)");
      context.registerService(EventHandler.class.getName(),
                              handler, props);
   }

   public void stop(BundleContext context) throws Exception {
   }

}
