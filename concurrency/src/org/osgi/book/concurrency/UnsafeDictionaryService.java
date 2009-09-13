package org.osgi.book.concurrency;

import java.util.HashMap;
import java.util.Map;

public class UnsafeDictionaryService implements DictionaryService {

   private Map<String,String> map;

   public void addDefinition(String word, String definition) {
      if(map == null) map = new HashMap<String, String>();
      
      map.put(word, definition);
   }

   public String lookup(String word) {
      return map == null ? null : map.get(word);
   }
}
