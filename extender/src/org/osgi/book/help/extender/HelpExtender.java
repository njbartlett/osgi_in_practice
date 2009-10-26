package org.osgi.book.help.extender;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.book.utils.Pair;
import org.osgi.framework.*;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.BundleTracker;

public class HelpExtender extends BundleTracker {

   private final Map<Long, List<Pair<URL, String>>> documentMap
         = new ConcurrentHashMap<Long, List<Pair<URL, String>>>();
   private final LogService log;

   public HelpExtender(BundleContext context, LogService log) {
      super(context, Bundle.ACTIVE, null);
      this.log = log;
   }
   
   public List<Pair<URL, String>> listHelpDocs() {
      List<Pair<URL, String>> result = new ArrayList<Pair<URL, String>>();
      for (List<Pair<URL, String>> list : documentMap.values())
         result.addAll(list);
      return result;
   }
   
   @Override
   public Object addingBundle(Bundle bundle, BundleEvent event) {
      Bundle result = null;
      long id = bundle.getBundleId();
      try {
         List<Pair<URL, String>> docs = scanForHelpDocsWithTitle(bundle);
         if (!docs.isEmpty()) {
            documentMap.put(id, docs);
            result = bundle;
         }
      } catch (IOException e) {
         log.log(LogService.LOG_ERROR, "IO error in bundle "
               + bundle.getLocation(), e);
      } catch (HelpScannerException e) {
         log.log(LogService.LOG_ERROR, "Error in bundle "
               + bundle.getLocation(), e);
      }
      return result;
   }

   @Override
   public void removedBundle(Bundle bundle, BundleEvent event, Object obj) {
      documentMap.remove(bundle.getBundleId());
   }

   // Omitted: scanForHelpDocsWithTitle method from previous section
   // ...

   private static final String HELP_INDEX_BUNDLE_HEADER = "Help-Index";

   private List<Pair<URL, String>> scanForHelpDocsWithTitle(
         Bundle bundle) throws IOException, HelpScannerException {
      @SuppressWarnings("unchecked")
      Dictionary<String, String> headers = bundle.getHeaders();

      // Find the index file entry; exit if not found
      String indexPath = headers.get(HELP_INDEX_BUNDLE_HEADER);
      if (indexPath == null)
         return Collections.emptyList();
      URL indexEntry = bundle.getEntry(indexPath);
      if (indexEntry == null)
         throw new HelpScannerException("Entry not found: " + indexPath);

      // Calculate the directory prefix
      int slashIndex = indexPath.lastIndexOf('/');
      String prefix = (slashIndex == -1)
            ? "" : indexPath.substring(0, slashIndex);

      // Load the index file as a Properties object
      Properties indexProps = new Properties();
      InputStream stream = null;
      try {
         stream = indexEntry.openStream();
         indexProps.load(stream);
      } finally {
         if (stream != null) stream.close();
      }

      // Iterate through the files
      List<Pair<URL, String>> result =
            new ArrayList<Pair<URL, String>>(indexProps.size());
      Enumeration<?> names = indexProps.propertyNames();
      while (names.hasMoreElements()) {
         String name = (String) names.nextElement();
         String title = indexProps.getProperty(name);

         URL entry = bundle.getEntry(prefix + "/" + name + ".html");
         if (entry != null) result.add(new Pair<URL, String>(entry, title));
      }
      return result;
   }


}