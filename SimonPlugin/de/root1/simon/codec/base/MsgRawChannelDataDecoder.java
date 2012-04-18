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

import java.nio.ByteBuffer;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.root1.simon.codec.messages.AbstractMessage;
import de.root1.simon.codec.messages.MsgRawChannelData;
import de.root1.simon.codec.messages.SimonMessageConstants;

/**
 * A {@link MessageDecoder} that decodes {@link MsgRawChannelData}.
 *
 * @author ACHR
 */
public class MsgRawChannelDataDecoder extends AbstractMessageDecoder {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public MsgRawChannelDataDecoder() {
        super(SimonMessageConstants.MSG_RAW_CHANNEL_DATA);
    }

    @Override
    protected AbstractMessage decodeBody(IoSession session, IoBuffer in) {
        MsgRawChannelData message = new MsgRawChannelData();
        
        message.setSequence(getCurrentSequence());

        int dataSize = getBodySize()-4; // not counting the 4 bytes from the channel token
        logger.trace("datasize={}", dataSize);

        int channelToken = in.getInt();
        logger.trace("channelToken={}", channelToken);
        message.setChannelToken(channelToken);

        byte[] b = new byte[dataSize];
        in.get(b);
        message.setData(ByteBuffer.allocate(dataSize).put(b));
        logger.trace("message={}", message);
            
        return message;
    }

    @Override
    public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
    }
}
