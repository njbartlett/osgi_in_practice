package org.osgi.book.utils;

import org.osgi.framework.BundleContext;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.util.tracker.ServiceTracker;

public class EventAdminTracker extends ServiceTracker
                               implements EventAdmin {
   
   public EventAdminTracker(BundleContext context) {
      super(context, EventAdmin.class.getName(), null);
   }

   public void postEvent(Event event) {
      EventAdmin evtAdmin = (EventAdmin) getService();
      if(evtAdmin != null) evtAdmin.postEvent(event);
   }

   public void sendEvent(Event event) {
      EventAdmin evtAdmin = (EventAdmin) getService();
      if(evtAdmin != null) evtAdmin.sendEvent(event);
   }
}
