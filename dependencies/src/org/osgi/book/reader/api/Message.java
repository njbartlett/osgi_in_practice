package org.osgi.book.reader.api;

import java.io.InputStream;

public interface Message {

   /**
    * @return The unique (within this message's mailbox) message ID.
    */
   long getId();
   
   /**
    * @return A human-readable text summary of the message. In some
    *         messaging systems this would map to the "subject" field.
    */
   String getSummary();

   /**
    * @return The Internet MIME type of the message content.
    */
   String getMIMEType();

   /**
    * Access the content of the message.
    * 
    * @throws MessageReaderException
    */
   InputStream getContent() throws MessageReaderException;

}
