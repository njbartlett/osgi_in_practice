package org.osgi.book.concurrency;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SafeDictionaryService implements DictionaryService {

   private final Map<String,String> map =
      Collections.synchronizedMap(new HashMap<String,String>());

   public void addDefinition(String word, String definition) {
      map.put(word, definition);
   }

   public String lookup(String word) {
      return map.get(word);
   }
}
