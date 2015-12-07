/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.mobicents.protocols.smpp.message;

import java.io.IOException;

import org.mobicents.protocols.smpp.util.PacketDecoder;
import org.mobicents.protocols.smpp.util.PacketEncoder;

/**
 * BroadcastSM response packet.
 * @version $Id: BroadcastSMResp.java 457 2009-01-15 17:37:42Z orank $
 * @since 0.4.0
 */
public class BroadcastSMResp extends SMPPPacket {
    private static final long serialVersionUID = 2L;
    
    private String messageId;
    
    public BroadcastSMResp() {
        super(CommandId.BROADCAST_SM_RESP);
    }
    
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
    
    public String getMessageId() {
        return messageId;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = super.equals(obj);
        if (equals) {
            BroadcastSMResp other = (BroadcastSMResp) obj;
            equals |= safeCompare(messageId, other.messageId);
        }
        return equals;
    }
    
    @Override
    public int hashCode() {
        return (messageId != null) ? messageId.hashCode() : "".hashCode();
    }
    
    @Override
    protected void readMandatory(PacketDecoder decoder) {
        messageId = decoder.readCString();
    }
    
    @Override
    protected void writeMandatory(PacketEncoder encoder) throws IOException {
        encoder.writeCString(messageId);
    }
    
    @Override
    protected int getMandatorySize() {
        return 1 + sizeOf(messageId);
    }
}
