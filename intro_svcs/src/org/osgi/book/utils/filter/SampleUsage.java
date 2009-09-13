package org.osgi.book.utils.filter;

import org.osgi.book.reader.api.Mailbox;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.InvalidSyntaxException;

public class SampleUsage {
   public void sample(BundleContext context) throws InvalidSyntaxException {
   context.createFilter(String.format("(&(%s=%s)(%s=%s)(%s=%s))",
         Constants.OBJECTCLASS, Mailbox.class.getName(),
         Mailbox.NAME_PROPERTY, "welcome",
         "lang", "en*"));
   }
}
