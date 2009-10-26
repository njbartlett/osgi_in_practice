package org.osgi.book.help.extender;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.osgi.book.utils.Pair;
import org.osgi.framework.Bundle;

public class HelpExtenderVer1 {

   private List<URL> scanForHelpDocs(Bundle bundle) {
      List<URL> result;
      Enumeration<?> entries = bundle.getEntryPaths("help");
      if (entries != null) {
         result = new ArrayList<URL>();
         while (entries.hasMoreElements()) {
            String entry = (String) entries.nextElement();
            if (entry.endsWith(".html")) {
               result.add(bundle.getEntry(entry));
            }
         }
      } else {
         result = Collections.emptyList();
      }
      return result;
   }

   private List<Pair<URL, String>> scanForHelpDocsWithTitles(
         Bundle bundle) throws IOException {
      // Find the index file entry; exit if not found
      URL indexEntry = bundle.getEntry("help/index.properties");
      if (indexEntry == null) {
         return Collections.emptyList();
      }

      // Load the index file as a Properties object
      Properties indexProps = new Properties();
      InputStream stream = null;
      try {
         stream = indexEntry.openStream();
         indexProps.load(stream);
      } finally {
         if (stream != null)
            stream.close();
      }

      // Iterate through the files
      List<Pair<URL, String>> result =
            new ArrayList<Pair<URL, String>>(indexProps.size());
      Enumeration<?> names = indexProps.propertyNames();
      while (names.hasMoreElements()) {
         String name = (String) names.nextElement();
         String title = indexProps.getProperty(name);

         URL entry = bundle.getEntry("help/" + name + ".html");
         if (entry != null) {
            result.add(new Pair<URL, String>(entry, title));
         }
      }

      return result;
   }

}