package doodledebugplugin;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PluginServer extends Remote {
	void showHtml(String html) throws RemoteException;
}
