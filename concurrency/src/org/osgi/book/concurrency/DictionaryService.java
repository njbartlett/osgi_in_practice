package org.osgi.book.concurrency;

public interface DictionaryService {
   void addDefinition(String word, String definition);
   String lookup(String word);
}