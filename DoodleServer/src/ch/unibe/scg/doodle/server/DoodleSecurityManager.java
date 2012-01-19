package ch.unibe.scg.doodle.server;

import java.security.Permission;

public class DoodleSecurityManager extends SecurityManager {
	
	public void checkPermission(Permission perm) {
		// be liberal
	}
	
	public void checkPermission(Permission perm, Object context) {
		// be liberal
	}
}
