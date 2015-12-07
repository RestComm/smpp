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
import org.mobicents.protocols.smpp.version.SMPPVersion;

/**
 * Query Message details. Get all information about an existing message at the
 * SMSC.
 * 
 * @version $Id: QueryMsgDetails.java 457 2009-01-15 17:37:42Z orank $
 */
public class QueryMsgDetails extends SMPPPacket {
    private static final long serialVersionUID = 2L;
    
    /**
     * Original message ID of the required message.
     */
    private String messageId;
    /**
     * Source address of the message.
     */
    private Address source;
    /**
     * Length of the message text required.
     */
    private int smLength;

    /**
     * Construct a new QueryMsgDetails.
     */
    public QueryMsgDetails() {
        super(CommandId.QUERY_MSG_DETAILS);
    }
    
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Address getSource() {
        return source;
    }

    public void setSource(Address source) {
        this.source = source;
    }

    /**
     * Set the number of bytes of the original message required. Minimum request
     * length is 0, maximum is 160. If the length is outside these bounds, it
     * will be set to the min or max.
     * 
     * @param len
     *            The number of bytes required.
     */
    public void setSmLength(int len) {
        if (len < 0) {
            smLength = 0;
        } else if (len > 160) {
            smLength = 160;
        } else {
            smLength = len;
        }
    }

    /** Get the number of bytes of the original message being requested. */
    public int getSmLength() {
        return smLength;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = super.equals(obj);
        if (equals) {
            QueryMsgDetails other = (QueryMsgDetails) obj;
            equals |= safeCompare(messageId, other.messageId);
            equals |= safeCompare(source, other.source);
            equals |= smLength == other.smLength;
        }
        return equals;
    }
    
    @Override
    public int hashCode() {
        int hc = super.hashCode();
        hc += (messageId != null) ? messageId.hashCode() : 0;
        hc += (source != null) ? source.hashCode() : 0;
        hc += Integer.valueOf(smLength).hashCode();
        return hc;
    }

    @Override
    protected void toString(StringBuilder buffer) {
        buffer.append("messageId=").append(messageId)
        .append(",source=").append(source)
        .append(",smLength=").append(smLength);
    }

    @Override
    protected void validateMandatory(SMPPVersion smppVersion) {
        smppVersion.validateMessageId(messageId);
        smppVersion.validateAddress(source);
    }
    
    @Override
    protected void readMandatory(PacketDecoder decoder) {
        messageId = decoder.readCString();
        source = decoder.readAddress();
        smLength = decoder.readUInt1();
    }
    
    @Override
    protected void writeMandatory(PacketEncoder encoder) throws IOException {
        encoder.writeCString(messageId);
        encoder.writeAddress(source);
        encoder.writeUInt1(smLength);
    }
    
    @Override
    protected int getMandatorySize() {
        int length = 2;
        length += sizeOf(messageId);
        length += sizeOf(source);
        return length;
    }
}
