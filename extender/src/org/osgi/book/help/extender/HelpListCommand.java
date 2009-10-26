package org.osgi.book.help.extender;

import java.net.URL;
import java.util.List;

import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;
import org.osgi.book.utils.Pair;

public class HelpListCommand implements CommandProvider {
   
   private final HelpExtender extender;

   public HelpListCommand(HelpExtender extender) {
      this.extender = extender;
   }
   
   public String getHelp() {
      return "\t" + "helpDocs - List currently available help docs";
   }
   
   public void _helpDocs(CommandInterpreter ci) {
      List<Pair<URL,String>> docs = extender.listHelpDocs();
      ci.println(docs.size() + " document(s) found");
      for (Pair<URL, String> pair : docs) {
         ci.println(pair.getSecond() + " (" + pair.getFirst() + ")");
      }
   }
}