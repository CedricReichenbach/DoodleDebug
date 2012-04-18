/*
 * Copyright (C) 2008 Alexander Christian <alex(at)root1.de>. All rights reserved.
 * 
 * This file is part of SIMON.
 *
 *   SIMON is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   SIMON is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with SIMON.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.root1.simon;

import de.root1.simon.utils.Utils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used by an endpoint if a remote object has to be "transferred" to the 
 * opposite endpoint. In such case, only the interface name is relevant. So an 
 * instance of this class is transferred instead of the "real" implementation of the remote object.
 * 
 * @author ACHR
 */
public class SimonRemoteInstance implements Serializable {

    private static final long serialVersionUID = 1;
    private transient final Logger logger = LoggerFactory.getLogger(getClass());
    /** Name of the interface that is used to implement the remote object */
    private List<String> interfaceNames = new ArrayList<String>();
    /** a unique identifier for the corresponding remote object */
    private String id = null;
    /** the remote object name of the simon proxy to which the SimonRemote belongs */
    private String remoteObjectName = null;

    /**
     *
     * Creates a new SimonRemoteInstance transport object
     *
     * @param session the {@link IoSession} to which the remote object is related to
     * @param remoteObject the remote object for which we generate this transport object for
     */
    protected SimonRemoteInstance(IoSession session, Object remoteObject) {
        logger.debug("begin");

        /*
         * try to get an name for this object.
         * The name is used by the equals-method in ProcessMessageRunnable to get an instance to compare with.
         * As this does only make sense in case of it's a object that has been explicitly bound to registry, it's save
         * to have a non working remote object name in case of any other implicit remote object
         */
        try {
            remoteObjectName = Simon.getSimonProxy(remoteObject).getRemoteObjectName();
        } catch (IllegalArgumentException e) {
            remoteObjectName = "{SimonRemoteInstance:RemoteObjectNameNotAvailable}";
        }

        String IP = session.getRemoteAddress().toString();
        long sessionId = session.getId();

        StringBuilder sb = new StringBuilder();

        sb.append("[");
        sb.append(remoteObject.getClass().getName());
        sb.append("|ip=");
        sb.append(IP);
        sb.append(";sessionID=");
        sb.append(sessionId);
        sb.append(";remoteObjectHash=");
        sb.append(remoteObject.hashCode());
        sb.append("]");

        this.id = sb.toString();

        logger.debug("SimonRemoteInstance created with id={}", this.id);

        /*
         * first, check all prerequisites to then decide how the interfaces names are gathered
         */
        // check for manually marked object
        SimonRemoteMarker marker = Utils.getMarker(remoteObject);
        Class[] remoteInterfacesInAnnotation=null;

        // check for annotations
        boolean isAnnotated = Utils.isRemoteAnnotated(remoteObject);
        if (isAnnotated) {
            de.root1.simon.annotation.SimonRemote annotation = remoteObject.getClass().getAnnotation(de.root1.simon.annotation.SimonRemote.class);
            remoteInterfacesInAnnotation = annotation.value();
            logger.trace("SimonRemoteObject is annotated with SimonRemote");
        }

        /*
         * get the interfaces names ...
         */

        if (marker!=null) { // if it's a marked class, read the interfaces from it's marker

            logger.debug("Provided remote object is a marked object.");
            Utils.putAllInterfaceNames(marker.getObjectToBeMarked(), interfaceNames);
            logger.debug("Got interfaces: {}", interfaceNames);
            
        } else if (remoteInterfacesInAnnotation!=null && remoteInterfacesInAnnotation.length>0) { // check for defined interfaces in annotation

            logger.trace("SimonRemoteObject has defined interfaces in it's annotation");

            for (Class interfaceClazz : remoteInterfacesInAnnotation) {
                String clazzName = interfaceClazz.getCanonicalName();
                logger.trace("Adding {} to the list of remote interfaces", clazzName);
                interfaceNames.add(clazzName);
            }

        } else { // need to manually search for useable interfaces

            logger.trace("Need to manually search for remote interfaces ...");

            if (isAnnotated) { // if it's annotated, but no interface was explicitly specified, we have to use all known interfaces
                
                logger.trace("Getting all (sub)interfaces...");
                Utils.putAllInterfaceNames(remoteObject, interfaceNames);

            } else {

                logger.trace("Searching for explicit remote interfaces marked with {} ...", de.root1.simon.SimonRemote.class.getCanonicalName());

                Class[] remoteInterfaces = remoteObject.getClass().getInterfaces();

                // check each interface if THIS is the one which implements "SimonRemote"
                for (Class<?> interfaceClazz : remoteInterfaces) {


                    String remoteObjectInterfaceClassNameTemp = interfaceClazz.getCanonicalName();

                    logger.trace("Checking interfacename='{}' for '{}'", remoteObjectInterfaceClassNameTemp, de.root1.simon.SimonRemote.class.getCanonicalName());

                    // Get the interfaces of the implementing interface
                    Class<?>[] remoteObjectInterfaceSubInterfaces = interfaceClazz.getInterfaces();

                    for (Class<?> remoteObjectInterfaceSubInterface : remoteObjectInterfaceSubInterfaces) {

                        logger.trace("Checking child interfaces for '{}': child={}", remoteObjectInterfaceClassNameTemp, remoteObjectInterfaceSubInterface);

                        if (remoteObjectInterfaceSubInterface.equals(de.root1.simon.SimonRemote.class)) {
                            logger.trace("Adding {} to the list of remote interfaces", remoteObjectInterfaceClassNameTemp);
                            if (!interfaceNames.contains(remoteObjectInterfaceClassNameTemp)) {
                                interfaceNames.add(remoteObjectInterfaceClassNameTemp);
                            } else {
                                logger.trace("{} already in list. skipping.", remoteObjectInterfaceClassNameTemp);
                            }
                        }
                    }

                }
            }
        }
        logger.debug("end");
    }

    

    


    /**
     *
     * Returns the name of the interface of the remote object's implementation
     *
     * @return the remote object's interface
     */
    protected List<String> getInterfaceNames() {
        return interfaceNames;
    }

    /**
     *
     * Returns an unique identifier for this remote object. This is necessary to differ from two
     * remote objects with the same implementation
     *
     * @return a unique ID for the remote object
     */
    protected String getId() {
        return id;
    }

    /**
     * Returns the proxy's remote object name in the related lookup table.
     * This method is used by {@link ProcessMessageRunnable#processEquals() } to get an instance of this object from lookup table for comparison. 
     * @return the remote object name
     */
    protected String getRemoteObjectName() {
        return remoteObjectName;
    }
}
