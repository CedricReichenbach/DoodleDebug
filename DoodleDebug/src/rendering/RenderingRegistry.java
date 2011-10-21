package rendering;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

/**
 * Manages different Renderings, can be organized by user (e.g. bring in their own renderings)
 * @author Cedric Reichenbach
 *
 */
public class RenderingRegistry implements Registry {

	@Override
	public void bind(String name, Remote obj) throws RemoteException,
			AlreadyBoundException, AccessException {
		// TODO Auto-generated method stub

	}

	@Override
	public String[] list() throws RemoteException, AccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Remote lookup(String name) throws RemoteException,
			NotBoundException, AccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void rebind(String name, Remote obj) throws RemoteException,
			AccessException {
		// TODO Auto-generated method stub

	}

	@Override
	public void unbind(String name) throws RemoteException, NotBoundException,
			AccessException {
		// TODO Auto-generated method stub

	}

}
