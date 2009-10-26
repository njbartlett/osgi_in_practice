package org.osgi.book.help.extender;

public class HelpScannerException extends Exception {

   public HelpScannerException(String message, Throwable cause) {
      super(message, cause);
   }

   public HelpScannerException(String message) {
      super(message);
   }

   public HelpScannerException(Throwable cause) {
      super(cause);
   }

}
