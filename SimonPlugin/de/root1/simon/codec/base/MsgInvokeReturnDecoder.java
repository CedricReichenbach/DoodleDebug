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
package de.root1.simon.codec.base;

import de.root1.simon.Simon;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.root1.simon.codec.messages.AbstractMessage;
import de.root1.simon.codec.messages.MsgError;
import de.root1.simon.codec.messages.MsgInvokeReturn;
import de.root1.simon.codec.messages.SimonMessageConstants;
import de.root1.simon.utils.SimonClassLoaderHelper;
import de.root1.simon.utils.Utils;

/**
 * A {@link MessageDecoder} that decodes {@link MsgInvokeReturn}.
 *
 * @author ACHR
 */
public class MsgInvokeReturnDecoder extends AbstractMessageDecoder {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public MsgInvokeReturnDecoder() {
        super(SimonMessageConstants.MSG_INVOKE_RETURN);
    }

    @Override
    protected AbstractMessage decodeBody(IoSession session, IoBuffer in) {


        MsgInvokeReturn m = new MsgInvokeReturn();
        try {
            Object returnValue = in.getObject(SimonClassLoaderHelper.getClassLoader(Simon.class));
            m.setReturnValue(returnValue);
        } catch (ClassNotFoundException e) {
            MsgError error = new MsgError();
            error.setErrorMessage("Error while decoding invoke return: Not able to read invoke result due to ClassNotFoundException");
            error.setRemoteObjectName(null);
            error.setThrowable(e);
            return error;
        }
        logger.trace("message={}", m);
        return m;

    }

    @Override
    public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
    }
}
