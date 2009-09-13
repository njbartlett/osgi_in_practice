package org.osgi.book.reader.fixedmailbox;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.osgi.book.reader.api.Message;
import org.osgi.book.reader.api.MessageReaderException;

public class StringMessage implements Message {

   private static final String MIME_TYPE_TEXT = "text/plain";

   private final long id;
   private final String subject;
   private final String text;

   public StringMessage(long id, String subject, String text) {
      this.id = id;
      this.subject = subject;
      this.text = text;
   }

   public InputStream getContent() throws MessageReaderException {
      return new ByteArrayInputStream(text.getBytes());
   }

   public long getId() {
      return id;
   }

   public String getMIMEType() {
      return MIME_TYPE_TEXT;
   }

   public String getSummary() {
      return subject;
   }
}
