package org.osgi.book.reader.api {
  class Mailbox {
  }
  
  object ScalaSwingUtils {
    import javax.swing.SwingUtilities
    def inEDT(action: => Unit) = 
      if (EventQueue.isDispatchThread())
        action
      else
        SwingUtilities.invokeLater(new Runnable() {def run = action})
  }
}

package org.osgi.book.reader.tutorial.scala {
  import javax.swing._
  import org.osgi.framework._
  import org.osgi.util.tracker._
  import org.osgi.book.reader.api._
  import org.osgi.book.reader.api.ScalaSwingUtils._
  
  class JLabelMailboxCountTracker(ctx: BundleContext, label: JLabel)
      extends ServiceTracker(ctx, classOf[Mailbox].getName, null) {
    
    private var count = 0: int
    
    override
    def addingService(ref: ServiceReference): AnyRef = {
      var displayCount = 0
      synchronized { count += 1; displayCount = count }
      
      inEDT(label.setText("foo"))
      null
    }
    
    override
    def removedService(ref: ServiceReference, service: AnyRef) = {
      var displayCount = 0
      synchronized { count -= 1; displayCount = count }
      
      inEDT(label.setText("foo"))
    }
  }
}


