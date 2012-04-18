package example;

import gnu.cajo.invoke.Remote;
import gnu.cajo.utils.BaseItem;

// Example server item.  It doesn't do much, except illustrate one way to
// construct them.
public class TestItem extends BaseItem {
   Object proxy;
   public TestItem() {
      runnable = new MainThread() { // the item's main processing thread
         public void run() {
            while (!thread.isInterrupted()) try { // excellent practice!
               synchronized(thread) { thread.wait(); }
               Thread.sleep(500);
               System.out.print("\nProxy async call, result = ");
               System.out.println(
                  Remote.invoke(proxy, "callback", "Goodbye from server!"));
            } catch(Exception x) { x.printStackTrace(System.err); }
         }
      };
   }
   // All of the item's public methods are remotely callable, just as if the
   // object were local.  Below is the interface created by this object:
   public String callback(Object proxy, String message) {
      this.proxy = proxy;
      System.out.print("\nProxy async callback from ");
      try { System.out.print(java.rmi.server.RemoteServer.getClientHost()); }
      catch(java.rmi.server.ServerNotActiveException x) {
         System.out.print("local item");
      }
      System.out.println(", message = " + message);
      System.out.println();
      synchronized(thread) { thread.notify(); }
      return "Server sync acknowledgement!";
   }
   // supported for cannonical completeness
   public String getDescription() {
      return "This method defines one application specific method:\n\n" +
      "   String callback(Object proxy, String message);\n\n" +
      "It is expected to be called by its proxy, following its arrival\n" +
      "at its remote host.";
   }
   // All items should uniquely identify themselves, but it is not required.
   public String toString() { return "Test Item"; }
}
