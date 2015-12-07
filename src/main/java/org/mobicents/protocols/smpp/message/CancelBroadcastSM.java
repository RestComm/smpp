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

import org.mobicents.protocols.smpp.Address;
import org.mobicents.protocols.smpp.util.PacketDecoder;
import org.mobicents.protocols.smpp.util.PacketEncoder;

/**
 * Cancel a previously submitted broadcast message.
 * @version $Id: CancelBroadcastSM.java 457 2009-01-15 17:37:42Z orank $
 * @since 0.4.0
 */
public class CancelBroadcastSM extends SMPPPacket {
    private static final long serialVersionUID = 2L;
    
    private String serviceType;
    private String messageId;
    private Address source;
    
    public CancelBroadcastSM() {
        super (CommandId.CANCEL_BROADCAST_SM);
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Address getSource() {
        return source;
    }

    public void setSource(Address source) {
        this.source = source;
    }
    
    @Override
    public boolean equals(Object obj) {
        boolean equals = super.equals(obj);
        if (equals) {
            CancelBroadcastSM other = (CancelBroadcastSM) obj;
            equals |= safeCompare(serviceType, other.serviceType);
            equals |= safeCompare(messageId, other.messageId);
            equals |= safeCompare(source, other.source);
        }
        return equals;
    }
    
    @Override
    public int hashCode() {
        int hc = super.hashCode();
        hc += (serviceType != null) ? serviceType.hashCode() : 0;
        hc += (messageId != null) ? messageId.hashCode() : 31;
        hc += (source != null) ? source.hashCode() : 97;
        return hc;
    }

    @Override
    protected void readMandatory(PacketDecoder decoder) {
        serviceType = decoder.readCString();
        messageId = decoder.readCString();
        source = decoder.readAddress();
    }
    
    @Override
    protected void writeMandatory(PacketEncoder encoder) throws IOException {
        encoder.writeCString(serviceType);
        encoder.writeCString(messageId);
        encoder.writeAddress(source);
    }
    
    @Override
    protected int getMandatorySize() {
        int length = 2;
        length += sizeOf(serviceType);
        length += sizeOf(messageId);
        length += sizeOf(source);
        return length;
    }
}
