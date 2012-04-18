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

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import org.apache.mina.core.future.CloseFuture;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.root1.simon.codec.messages.AbstractMessage;
import de.root1.simon.codec.messages.MsgCloseRawChannel;
import de.root1.simon.codec.messages.MsgCloseRawChannelReturn;
import de.root1.simon.codec.messages.MsgEquals;
import de.root1.simon.codec.messages.MsgEqualsReturn;
import de.root1.simon.codec.messages.MsgError;
import de.root1.simon.codec.messages.MsgHashCode;
import de.root1.simon.codec.messages.MsgHashCodeReturn;
import de.root1.simon.codec.messages.MsgInterfaceLookup;
import de.root1.simon.codec.messages.MsgInterfaceLookupReturn;
import de.root1.simon.codec.messages.MsgInvoke;
import de.root1.simon.codec.messages.MsgInvokeReturn;
import de.root1.simon.codec.messages.MsgNameLookup;
import de.root1.simon.codec.messages.MsgNameLookupReturn;
import de.root1.simon.codec.messages.MsgOpenRawChannel;
import de.root1.simon.codec.messages.MsgOpenRawChannelReturn;
import de.root1.simon.codec.messages.MsgRawChannelData;
import de.root1.simon.codec.messages.MsgRawChannelDataReturn;
import de.root1.simon.codec.messages.MsgToString;
import de.root1.simon.codec.messages.MsgToStringReturn;
import de.root1.simon.codec.messages.SimonMessageConstants;
import de.root1.simon.exceptions.LookupFailedException;
import de.root1.simon.exceptions.SessionException;
import de.root1.simon.exceptions.SimonException;
import de.root1.simon.exceptions.SimonRemoteException;
import de.root1.simon.utils.SimonClassLoaderHelper;
import de.root1.simon.utils.Utils;
import java.lang.reflect.UndeclaredThrowableException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * This class is feed with all kind of messages (requests/invokes and returns)
 * and is then run on a thread pool.
 * 
 * The message gets then processed and answered. Either ProcessMessageRunnable 
 * invokes the requested method and returns the result to the remote, or it 
 * passes the result to the dispatcher where then the requesting call is getting
 * answered.
 * 
 * @author achr
 *
 */
public class ProcessMessageRunnable implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private AbstractMessage abstractMessage;
    private IoSession session;
    private Dispatcher dispatcher;

    protected ProcessMessageRunnable(Dispatcher dispatcher, IoSession session, AbstractMessage abstractMessage) {
        this.dispatcher = dispatcher;
        this.session = session;
        this.abstractMessage = abstractMessage;
    }

    @Override
    public void run() {

        logger.debug("ProcessMessageRunnable: {} on sessionId {}", abstractMessage, Utils.longToHexString(session.getId()));

        int msgType = abstractMessage.getMsgType();

        switch (msgType) {

            case SimonMessageConstants.MSG_NAME_LOOKUP:
                processNameLookup();
                break;

            case SimonMessageConstants.MSG_INTERFACE_LOOKUP:
                processInterfaceLookup();
                break;

            case SimonMessageConstants.MSG_NAME_LOOKUP_RETURN:
                processNameLookupReturn();
                break;

            case SimonMessageConstants.MSG_INTERFACE_LOOKUP_RETURN:
                processInterfaceLookupReturn();
                break;

            case SimonMessageConstants.MSG_INVOKE:
                processInvoke();
                break;

            case SimonMessageConstants.MSG_INVOKE_RETURN:
                processInvokeReturn();
                break;

            case SimonMessageConstants.MSG_TOSTRING:
                processToString();
                break;

            case SimonMessageConstants.MSG_TOSTRING_RETURN:
                processToStringReturn();
                break;

            case SimonMessageConstants.MSG_EQUALS:
                processEquals();
                break;

            case SimonMessageConstants.MSG_EQUALS_RETURN:
                processEqualsReturn();
                break;

            case SimonMessageConstants.MSG_HASHCODE:
                processHashCode();
                break;

            case SimonMessageConstants.MSG_HASHCODE_RETURN:
                processHashCodeReturn();
                break;

            case SimonMessageConstants.MSG_OPEN_RAW_CHANNEL:
                processOpenRawChannel();
                break;

            case SimonMessageConstants.MSG_OPEN_RAW_CHANNEL_RETURN:
                processOpenRawChannelReturn();
                break;

            case SimonMessageConstants.MSG_CLOSE_RAW_CHANNEL:
                processCloseRawChannel();
                break;

            case SimonMessageConstants.MSG_CLOSE_RAW_CHANNEL_RETURN:
                processCloseRawChannelReturn();
                break;

            case SimonMessageConstants.MSG_RAW_CHANNEL_DATA:
                processRawChannelData();
                break;

            case SimonMessageConstants.MSG_RAW_CHANNEL_DATA_RETURN:
                processRawChannelDataReturn();
                break;

            case SimonMessageConstants.MSG_PING:
                processPing();
                break;

            case SimonMessageConstants.MSG_PONG:
                processPong();
                break;
                
            case SimonMessageConstants.MSG_ERROR:
                processError();
                break;                

            default:
                // FIXME what to do here ?!
                logger.error("ProcessMessageRunnable: msgType={} not supported! terminating...", msgType);
                System.exit(1);
                break;
        }

    }

    private void processRawChannelDataReturn() {
        logger.debug("begin");

        logger.debug("processing MsgRawChannelDataReturn...");
        MsgRawChannelDataReturn msg = (MsgRawChannelDataReturn) abstractMessage;
        dispatcher.putResultToQueue(session, msg.getSequence(), msg);

        logger.debug("put result to queue={}", msg);

        logger.debug("end");
    }

    private void processPing() {
        logger.debug("begin");
        logger.debug("processing MsgPing...");

        logger.debug("replying pong");
        try {
            dispatcher.sendPong(session);
        } catch (SessionException e) {
            logger.warn("could not reply pong for seqId {}. Error was: {}", abstractMessage.getSequence(), e.getMessage());
        }

        logger.debug("end");
    }

    private void processPong() {
        logger.debug("begin");
        logger.debug("processing MsgPong...");

        dispatcher.getPingWatchdog().notifyPongReceived(session);
        logger.debug("end");
    }

    private void processOpenRawChannel() {
        logger.debug("begin");

        logger.debug("processing MsgOpenRawChannel...");
        MsgOpenRawChannel msg = (MsgOpenRawChannel) abstractMessage;

        MsgOpenRawChannelReturn returnMsg = new MsgOpenRawChannelReturn();
        returnMsg.setSequence(msg.getSequence());
        returnMsg.setReturnValue(dispatcher.isRawChannelDataListenerRegistered(msg.getChannelToken()));
        session.write(returnMsg);

        logger.debug("end");
    }

    private void processOpenRawChannelReturn() {
        logger.debug("begin");
        logger.debug("processing MsgOpenRawChannelReturn...");
        MsgOpenRawChannelReturn msg = (MsgOpenRawChannelReturn) abstractMessage;
        dispatcher.putResultToQueue(session, msg.getSequence(), msg);
        logger.debug("put result to queue={}", msg);
        logger.debug("end");
    }

    private void processCloseRawChannel() {
        logger.debug("begin");

        logger.debug("processing MsgCloseRawChannel...");
        MsgCloseRawChannel msg = (MsgCloseRawChannel) abstractMessage;

        dispatcher.unprepareRawChannel(msg.getChannelToken());

        MsgCloseRawChannelReturn returnMsg = new MsgCloseRawChannelReturn();
        returnMsg.setSequence(msg.getSequence());
        returnMsg.setReturnValue(true);
        session.write(returnMsg);

        logger.debug("end");
    }

    private void processCloseRawChannelReturn() {
        logger.debug("begin");
        logger.debug("processing MsgCloseRawChannelReturn...");
        MsgCloseRawChannelReturn msg = (MsgCloseRawChannelReturn) abstractMessage;
        dispatcher.putResultToQueue(session, msg.getSequence(), msg);
        logger.debug("put result to queue={}", msg);
        logger.debug("end");
    }

    private void processRawChannelData() {
        logger.debug("begin");

        logger.debug("processing MsgRawChannelData...");
        MsgRawChannelData msg = (MsgRawChannelData) abstractMessage;

        RawChannelDataListener rawChannelDataListener = dispatcher.getRawChannelDataListener(msg.getChannelToken());
        if (rawChannelDataListener != null) {
            logger.debug("writing data to {} for token {}.", rawChannelDataListener, msg.getChannelToken());
            ByteBuffer data = msg.getData();
            data.flip();
            rawChannelDataListener.write(data);
            logger.debug("data forwarded to listener for token {}", msg.getChannelToken());
            MsgRawChannelDataReturn returnMsg = new MsgRawChannelDataReturn();
            returnMsg.setSequence(msg.getSequence());
            session.write(returnMsg);
        } else {
            logger.error("trying to forward data to a not registered or already closed listener: token={} data={}", msg.getChannelToken(), msg.getData());
        }

        logger.debug("end");
    }

    /**
     * processes a name lookup
     */
    private void processNameLookup() {
        logger.debug("begin");

        logger.debug("processing MsgLookup...");
        MsgNameLookup msg = (MsgNameLookup) abstractMessage;
        String remoteObjectName = msg.getRemoteObjectName();

        logger.debug("Sending result for remoteObjectName={}", remoteObjectName);

        MsgNameLookupReturn ret = new MsgNameLookupReturn();
        ret.setSequence(msg.getSequence());
        try {
            Class<?>[] interfaces = null;

            interfaces = Utils.findAllRemoteInterfaces(dispatcher.getLookupTable().getRemoteObjectContainer(remoteObjectName).getRemoteObject().getClass());

            ret.setInterfaces(interfaces);
        } catch (LookupFailedException e) {
            logger.debug("Lookup for remote object '{}' failed: {}", remoteObjectName, e.getMessage());
            ret.setErrorMsg("Error: " + e.getClass() + "->" + e.getMessage() + "\n" + Utils.getStackTraceAsString(e));
        }
        session.write(ret);

        logger.debug("end");
    }

    /**
     * processes a interface lookup
     */
    private void processInterfaceLookup() {
        logger.debug("begin");

        logger.debug("processing MsgInterfaceLookup...");
        MsgInterfaceLookup msg = (MsgInterfaceLookup) abstractMessage;
        String canonicalInterfaceName = msg.getCanonicalInterfaceName();

        logger.debug("Sending result for interfaceName={}", canonicalInterfaceName);

        MsgInterfaceLookupReturn ret = new MsgInterfaceLookupReturn();
        ret.setSequence(msg.getSequence());
        try {

            RemoteObjectContainer container = dispatcher.getLookupTable().getRemoteObjectContainerByInterface(canonicalInterfaceName);

            ret.setInterfaces(container.getRemoteObjectInterfaces());
            ret.setRemoteObjectName(container.getRemoteObjectName());

        } catch (LookupFailedException e) {
            logger.debug("Lookup for remote object '{}' failed: {}", canonicalInterfaceName, e.getMessage());
            ret.setErrorMsg("Error: " + e.getClass() + "->" + e.getMessage() + "\n" + Utils.getStackTraceAsString(e));
        }
        session.write(ret);

        logger.debug("end");
    }

    private void processNameLookupReturn() {
        logger.debug("begin");

        logger.debug("processing MsgNameLookupReturn...");
        MsgNameLookupReturn msg = (MsgNameLookupReturn) abstractMessage;

        logger.debug("Forward result to waiting monitor");
        dispatcher.putResultToQueue(session, msg.getSequence(), msg);

        logger.debug("end");
    }

    private void processInterfaceLookupReturn() {
        logger.debug("begin");

        logger.debug("processing MsgInterfaceLookupReturn...");
        MsgInterfaceLookupReturn msg = (MsgInterfaceLookupReturn) abstractMessage;

        logger.debug("Forward result to waiting monitor");
        dispatcher.putResultToQueue(session, msg.getSequence(), msg);

        logger.debug("end");
    }

    /**
     * This method is processed on the remote end that finally calls the method
     * and returns the result to the calling end.
     */
    private void processInvoke() {
        logger.debug("begin");

        logger.debug("processing MsgInvoke...");

        Object result = null;

        MsgInvoke msg = (MsgInvoke) abstractMessage;

        // if received msg has an error
        if (msg.hasError()) {
            result = new SimonRemoteException("Received MsgInvoke had errors. Cannot process invocation. error msg: " + msg.getErrorMsg());

            MsgInvokeReturn returnMsg = new MsgInvokeReturn();
            returnMsg.setSequence(msg.getSequence());

            returnMsg.setReturnValue(result);

            logger.debug("Sending result={}", returnMsg);

            session.write(returnMsg);
            logger.debug("end");
            return;
        }

        Method method = msg.getMethod();
        Object[] arguments = msg.getArguments();
        String remoteObjectName = msg.getRemoteObjectName();

        try {

            // ------------
            // replace existing SimonRemote objects with proxy object
            if (arguments != null) {

                for (int i = 0; i < arguments.length; i++) {

                    // search the arguments for remote instances
                    if (arguments[i] instanceof SimonRemoteInstance) {

                        final SimonRemoteInstance simonCallback = (SimonRemoteInstance) arguments[i];

                        logger.debug("SimonCallback in args found. id={}", simonCallback.getId());

                        List<String> interfaceNames = simonCallback.getInterfaceNames();
                        Class<?>[] listenerInterfaces = new Class<?>[interfaceNames.size()];
                        for (int j = 0; j < interfaceNames.size(); j++) {
                            listenerInterfaces[j] = Class.forName(interfaceNames.get(j));
                        }

                        // re-implant the proxy object
                        arguments[i] = Proxy.newProxyInstance(SimonClassLoaderHelper.getClassLoader(this.getClass()), listenerInterfaces, new SimonProxy(dispatcher, session, simonCallback.getId(), listenerInterfaces));
                        logger.debug("proxy object for SimonCallback injected");
                    }
                }
            }
            // ------------

            logger.debug("ron={} method={} args={}", new Object[]{remoteObjectName, method, arguments});

            Object remoteObject = dispatcher.getLookupTable().getRemoteObjectContainer(remoteObjectName).getRemoteObject();

            try {
                result = method.invoke(remoteObject, arguments);
            } catch (IllegalArgumentException ex) {
                logger.error("IllegalArgumentException while invoking remote method. Arguments obviously do not match the methods parameter types. Errormsg: "+ex.getMessage());
                logger.error("***** Analysis of arguments and paramtypes ... ron={} method={} ", remoteObjectName, method.getName());
                if (arguments != null && arguments.length!=0) {
                    for (int i = 0; i < arguments.length; i++) {
                        logger.error("***** arguments[" + i + "]: " + (arguments[i] == null ? "null" : arguments[i].getClass().getCanonicalName()) + " toString: " + (arguments[i] == null ? "null" : arguments[i].toString()));
//                        if (arguments[i]!=null) {
//                            for (Method m : arguments[i].getClass().getMethods()){
//                                logger.error("***** arguments[" + i + "] has method: {}",m);
//                            }
//                        }
                    }
                } else {
                    logger.error("***** no arguments available.");
                }

                Class<?>[] paramType = method.getParameterTypes();
                if (paramType != null && paramType.length!=0) {
                    for (int i = 0; i < paramType.length; i++) {
//                        logger.error("***** paramType[" + i + "]: " + (paramType[i] == null ? "null" : paramType[i].getClass().getCanonicalName()));
                        logger.error("***** paramType[" + i + "]: " + (paramType[i] == null ? "null" : paramType[i].getCanonicalName()));
                    }
                } else {
                    logger.error("***** no paramtypes available.");
                }
                
                for (Method m : remoteObject.getClass().getMethods()){
                    logger.error("***** remoteObject '{}' has method: {}",remoteObjectName, m);
                }
                
                logger.error("***** method signature: {}", method.toString());
                logger.error("***** generic method signature: {}", method.toGenericString());
                logger.error("***** Analysis of arguments and paramtypes ... *DONE*");
                ex.printStackTrace();
                throw ex;
            }
//            catch (Throwable ex) {
//                result = Utils.getRootCause(ex);
//                System.err.println("undeclared throwable exception: root cause: "+result);
//            }
            
            if (Utils.isSimonProxy(result)) {
                throw new SimonException("Result of method '" + method + "' is a local endpoint of a remote object. Endpoints can not be transferred.");
            }

            if (method.getReturnType() == void.class) {
                result = new SimonVoid();
            }

            // register "SimonCallback"-results in lookup-table
            if (Utils.isValidRemote(result)) {

                logger.debug("Result of method '{}' is SimonRemote: {}", method, result);

                SimonRemoteInstance simonCallback = new SimonRemoteInstance(session, result);

                dispatcher.getLookupTable().putRemoteInstanceBinding(session.getId(), simonCallback.getId(), result);
                result = simonCallback;

            }

        } catch (IllegalArgumentException e) {
            result = e;
        } catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof UndeclaredThrowableException) {
                result = Utils.getRootCause(e.getTargetException());
            } else {
                result = e.getTargetException();
            }
        } catch (Exception e) { 
            SimonRemoteException sre = new SimonRemoteException("Errow while invoking '" + remoteObjectName + "#" + method + "' due to underlying exception: "+e.getClass());
            sre.initCause(e);
            result = sre;
        }

        // a return value can be "null" ... this has to be serialized to the client
        if (result != null && !(result instanceof Serializable)) {
            logger.warn("Result '{}' is not serializable", result);
            result = new SimonRemoteException("Result of method '" + method + "' must be serializable and therefore implement 'java.io.Serializable' or 'de.root1.simon.SimonRemote'");
        }
        
        MsgInvokeReturn returnMsg = new MsgInvokeReturn();
        returnMsg.setSequence(msg.getSequence());

        returnMsg.setReturnValue(result);

        logger.debug("Sending result={}", returnMsg);

        session.write(returnMsg);
        logger.debug("end");
    }

    /**
     * This method is triggered on caller end to retrieve the invocation result,
     * pass it to the result map and wake the caller thread
     */
    private void processInvokeReturn() {
        logger.debug("begin");

        logger.debug("processing MsgInvokeReturn...");
        MsgInvokeReturn msg = (MsgInvokeReturn) abstractMessage;
        dispatcher.putResultToQueue(session, msg.getSequence(), msg);

        logger.debug("put result to queue={}", msg);

        logger.debug("end");
    }

    private void processToString() {
        logger.debug("begin");

        logger.debug("processing MsgToString...");
        MsgToString msg = (MsgToString) abstractMessage;

        String remoteObjectName = msg.getRemoteObjectName();
        String returnValue = null;
        try {
            returnValue = dispatcher.getLookupTable().getRemoteObjectContainer(remoteObjectName).getRemoteObject().toString();
        } catch (LookupFailedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        MsgToStringReturn returnMsg = new MsgToStringReturn();
        returnMsg.setSequence(msg.getSequence());
        returnMsg.setReturnValue(returnValue);
        session.write(returnMsg);
        logger.debug("end");
    }

    private void processToStringReturn() {
        logger.debug("begin");
        logger.debug("processing MsgToStringReturn...");
        MsgToStringReturn msg = (MsgToStringReturn) abstractMessage;
        dispatcher.putResultToQueue(session, msg.getSequence(), msg);

        logger.debug("put result to queue={}", msg);

        logger.debug("end");
    }

    private void processEquals() {
        logger.debug("begin");

        logger.debug("processing MsgEquals...");
        MsgEquals msg = (MsgEquals) abstractMessage;

        String remoteObjectName = msg.getRemoteObjectName();
        Object objectToCompareWith = msg.getObjectToCompareWith();

        boolean equalsResult = false;
        try {
            
            if (objectToCompareWith instanceof SimonRemoteInstance) {
                SimonRemoteInstance sri = (SimonRemoteInstance) objectToCompareWith;
                logger.debug("Got a SimonRemoteInstance(ron='{}') to compare with, looking for real object...", sri.getRemoteObjectName());
                objectToCompareWith = dispatcher.getLookupTable().getRemoteObjectContainer(sri.getRemoteObjectName()).getRemoteObject();
            }

            Object tthis = dispatcher.getLookupTable().getRemoteObjectContainer(remoteObjectName).getRemoteObject();
            if (objectToCompareWith == null) {
                equalsResult = false;
            } else {
                equalsResult = tthis.equals(objectToCompareWith);
            }
            logger.debug("this='{}' objectToCompareWith='{}' equalsResult={}", new Object[]{tthis.toString(), (objectToCompareWith == null ? "NULL" : objectToCompareWith.toString()), equalsResult});


        } catch (LookupFailedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        MsgEqualsReturn returnMsg = new MsgEqualsReturn();
        returnMsg.setSequence(msg.getSequence());
        returnMsg.setEqualsResult(equalsResult);
        session.write(returnMsg);
        logger.debug("end");
    }

    private void processEqualsReturn() {
        logger.debug("begin");
        logger.debug("processing MsgEqualsReturn...");
        MsgEqualsReturn msg = (MsgEqualsReturn) abstractMessage;
        dispatcher.putResultToQueue(session, msg.getSequence(), msg);

        logger.debug("put result to queue={}", msg);

        logger.debug("end");
    }

    private void processHashCode() {
        logger.debug("begin");

        logger.debug("processing MsgHashCode...");
        MsgHashCode msg = (MsgHashCode) abstractMessage;

        String remoteObjectName = msg.getRemoteObjectName();

        MsgHashCodeReturn returnMsg = new MsgHashCodeReturn();
        returnMsg.setSequence(msg.getSequence());

        int returnValue = -1;
        try {
            returnValue = dispatcher.getLookupTable().getRemoteObjectContainer(remoteObjectName).getRemoteObject().hashCode();
        } catch (LookupFailedException e) {
            returnMsg.setErrorMsg("Failed looking up the remote object for getting the hash code. Error: " + e.getMessage() + "\n" + Utils.getStackTraceAsString(e));
        }

        returnMsg.setReturnValue(returnValue);
        session.write(returnMsg);
        logger.debug("end");
    }

    private void processHashCodeReturn() {
        logger.debug("begin");
        logger.debug("processing MsgHashCodeReturn...");
        MsgHashCodeReturn msg = (MsgHashCodeReturn) abstractMessage;
        dispatcher.putResultToQueue(session, msg.getSequence(), msg);

        logger.debug("put result to queue={}", msg);

        logger.debug("end");
    }

    private void processError() {
        logger.debug("begin");

        logger.debug("processing MsgError...");
        MsgError msg = (MsgError) abstractMessage;

        String remoteObjectName = msg.getRemoteObjectName();
        String errorMessage = msg.getErrorMessage();
        Throwable throwable = msg.getThrowable();
        boolean isDecodeError = msg.isDecodeError();
        
        String exceptionMessage = "";
        
        // if error happened on the local while reading a message
        if (isDecodeError) {
            if (remoteObjectName!=null && remoteObjectName.length()>0) {
                exceptionMessage = "An error occured while reading a message for remote object '"+remoteObjectName+"'. Error message: "+errorMessage;
            } else {
                exceptionMessage = "An error occured while reading a message. Error message: "+errorMessage;
            }
        // if error happened on remote while writing a message
        } else {
            if (remoteObjectName!=null && remoteObjectName.length()>0) {
                exceptionMessage = "An error occured on remote while writing a message to remote object '"+remoteObjectName+"'. Error message: "+errorMessage;
            } else {
                exceptionMessage = "An error occured on remote while writing a message. Error message: "+errorMessage;
            }
        }
        
        SimonException se = new SimonException(exceptionMessage);
        se.initCause(throwable);
        CloseFuture closeFuture = session.close(true);
        closeFuture.awaitUninterruptibly();
        logger.debug("end");
        throw se;
    }
}
