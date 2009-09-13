package org.osgi.book.reader.api;

public class MessageReaderException extends Exception {

   private static final long serialVersionUID = 1L;

   public MessageReaderException(String message) {
      super(message);
   }

   public MessageReaderException(Throwable cause) {
      super(cause);
   }

   public MessageReaderException(String message, Throwable cause) {
      super(message, cause);
   }

}
