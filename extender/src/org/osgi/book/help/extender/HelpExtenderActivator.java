package org.osgi.book.help.extender;

import org.eclipse.osgi.framework.console.CommandProvider;
import org.osgi.book.utils.LogTracker;
import org.osgi.framework.*;

public class HelpExtenderActivator implements BundleActivator {

   private volatile LogTracker log;
   private volatile HelpExtender extender;
   private volatile ServiceRegistration cmdSvcReg;

   public void start(BundleContext context) throws Exception {
      log = new LogTracker(context);
      log.open();
      extender = new HelpExtender(context, log);
      extender.open();

      HelpListCommand command = new HelpListCommand(extender);
      cmdSvcReg = context.registerService(CommandProvider.class.getName(),
            command, null);
   }

   public void stop(BundleContext context) throws Exception {
      cmdSvcReg.unregister();
      extender.close();
      log.close();
   }

}
